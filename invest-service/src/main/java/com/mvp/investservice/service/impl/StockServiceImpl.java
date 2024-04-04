package com.mvp.investservice.service.impl;

import com.mvp.investservice.domain.exception.*;
import com.mvp.investservice.service.StockService;
import com.mvp.investservice.service.cache.CacheService;
import com.mvp.investservice.util.MoneyParser;
import com.mvp.investservice.util.SectorStockUtil;
import com.mvp.investservice.web.dto.OrderResponse;
import com.mvp.investservice.web.dto.PurchaseDto;
import com.mvp.investservice.web.dto.SaleDto;
import com.mvp.investservice.web.dto.portfolio.PortfolioRequest;
import com.mvp.investservice.web.dto.stock.DividendDto;
import com.mvp.investservice.web.dto.stock.DividendResponse;
import com.mvp.investservice.web.dto.stock.StockDto;
import com.mvp.investservice.web.mapper.StockMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.*;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.mvp.investservice.util.MoneyParser.convertToBigDecimal;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final CacheService cacheService;

    @Getter
    private final InvestApi investApi;
    private final StockMapper stockMapper;
    private final PortfolioServiceImpl portfolioService;
    private final AccountServiceImpl accountService;

    @Override
    public List<StockDto>  getStocksByName(String name) {
        var stocksInfo = investApi.getInstrumentsService().findInstrumentSync(name)
                .stream().filter(b -> b.getInstrumentType().equalsIgnoreCase("share")).toList();
        if (stocksInfo.isEmpty()) {
            throw new ResourceNotFoundException("Не удалось найти акцию: " + name);
        }

        var tradableStocks = cacheService.getTradableStocksSync(investApi);
        List<String> stocksFigis = new ArrayList<>(stocksInfo.size());
        for (var stock : stocksInfo) {
            if (!tradableStocks.stream().filter(b -> b.getFigi().equalsIgnoreCase(stock.getFigi())).findFirst().isEmpty()) {
                stocksFigis.add(stock.getFigi());
            }
        }

        List<Share> stocks = new ArrayList<>();
        for (var figi : stocksFigis) {
            stocks.add(investApi.getInstrumentsService().getShareByFigiSync(figi));
        }

        return stockMapper.toDto(stocks, getLastPrices(stocksFigis));
    }

    @Override
    public List<StockDto> getStocks(Integer page, Integer count) {
        if (page < 1) {
            page = 1;
        }
        if (count < 1) {
            count = 10;
        }

        var tradableStocks = cacheService.getTradableStocksSync(investApi).subList((page - 1) * count, count);

        List<StockDto> stocks = new ArrayList<>();
        for (var stock : tradableStocks) {
            if (stock.getCurrency().equals("rub")) {
                stocks.add(stockMapper.toDto(stock, getLastPrice(stock.getFigi())));
            }
        }

        return stocks;
    }

    @Override
    public List<StockDto> getStocksBySector(String sectorName, Integer count) {
        if (count <= 0) {
            count = 10;
        }

        var sector = SectorStockUtil.valueOfRussianName(sectorName);
        var tradableStocks = cacheService.getTradableStocksSync(investApi);

        List<Share> stocksBySector = new ArrayList<>();

        for (var i = 0; i < tradableStocks.size() && stocksBySector.size() < count; i++) {
            var stock = tradableStocks.get(i);
            if (stock.getSector().equalsIgnoreCase(sector)) {
                stocksBySector.add(stock);
            }
        }

        List<StockDto> stocks = new ArrayList<>();
        for (var stock : stocksBySector) {
            var temp = stockMapper.toDto(stock, getLastPrice(stock.getFigi()));
            temp.setSector(SectorStockUtil.valueOfEnglishName(temp.getSector()));

            stocks.add(temp);
        }

        return stocks;
    }

    @Override
    public OrderResponse<StockDto> buyStock(PurchaseDto purchaseDto) throws InsufficientFundsException {
        Share shareToBuy;
        try {
            shareToBuy = investApi.getInstrumentsService()
                    .getShareByFigiSync(purchaseDto.getFigi());
        } catch (Exception e) {
            throw new AssetNotFoundException(e.getMessage());
        }

        if (shareToBuy.getBuyAvailableFlag() && shareToBuy.getApiTradeAvailableFlag()) {
            String figi = shareToBuy.getFigi();
            BigDecimal price = getBigDecimalPrice(figi);
            Quotation resultPrice = getPrice(price);
            PostOrderResponse postOrderResponse;

            var balance = accountService.getBalance(purchaseDto.getAccountId());
            if (balance.compareTo(price) <= 0) {
                throw new InsufficientFundsException(price, balance);
            }

            try {
                postOrderResponse = investApi.getOrdersService()
                        .postOrderSync(figi, purchaseDto.getLot(), resultPrice, OrderDirection.ORDER_DIRECTION_BUY, purchaseDto.getAccountId(), OrderType.valueOf(purchaseDto.getOrderType().name()), UUID.randomUUID().toString());
            } catch (Exception e) {
                throw new BuyUnavailableException(e.getMessage());
            }

            return generateOrderResponse(shareToBuy, postOrderResponse);
        } else {
            throw new BuyUnavailableException("В данный момент невозможно купить данную акцию");
        }
    }

    @Override
    public OrderResponse<StockDto> saleStock(SaleDto saleDto) {
        StockDto saleStock = null;
        try {
            var portfolio = new PortfolioRequest();
            portfolio.setAccountId(saleDto.getAccountId());

            var positions = portfolioService.getPortfolioPositions(portfolio);
            if (positions.isEmpty()) {
                throw new AssetNotFoundException("В портфеле отсутсвуют ценные бумаги");
            }

            for (var asset : portfolioService.getPortfolioPositions(portfolio)) {
                if (asset.getAsset() instanceof StockDto) {
                    if (((StockDto)asset.getAsset()).getFigi().equalsIgnoreCase(saleDto.getFigi())) {
                        saleStock = (StockDto)asset.getAsset();
                        break;
                    }
                }
            }

            if (saleStock == null) {
                throw new AssetNotFoundException("В портфеле отсутсвует выбранная акция");
            }

            var saleResponse = investApi.getOrdersService()
                    .postOrderSync(saleStock.getFigi(),
                            saleDto.getLot(),
                            getPrice(getBigDecimalPrice(saleStock.getFigi())),
                            OrderDirection.ORDER_DIRECTION_SELL,
                            saleDto.getAccountId(),
                            OrderType.valueOf(saleDto.getOrderType().name()),
                            UUID.randomUUID().toString());

            return generateOrderResponse(saleStock, saleResponse);

        } catch (Exception e) {
            throw new AssetNotFoundException(e.getMessage());
        }
    }

    @Override
    public DividendResponse getDividends(String figi) {
        if (investApi.getInstrumentsService().findInstrumentSync(figi)
                .stream().filter(s -> s.getInstrumentType().equalsIgnoreCase("share")
                                && s.getFigi().equalsIgnoreCase(figi)).toList().isEmpty()) {
            throw new AssetNotFoundException("Акция не найдена");
        }

        var dividendsAscDate = investApi.getInstrumentsService().getDividendsSync(figi, Instant.ofEpochSecond(0), Instant.now());
        if (dividendsAscDate.isEmpty()) {
            throw new YieldNotFoundException("Дивиденды по акции отстуствуют");
        }

        var dividendsDescDate = new ArrayList<>(dividendsAscDate);
        Collections.reverse(dividendsDescDate);

        return new DividendResponse(figi, generateDividendsDto(dividendsDescDate));
    }

    private List<DividendDto> generateDividendsDto(List<Dividend> dividends) {
        var dividendsDto = new ArrayList<DividendDto>();

        for (var div : dividends) {
            var dividendDto = new DividendDto();

            dividendDto.setDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(div.getLastBuyDate().getSeconds()),
                    ZoneId.systemDefault()));
            dividendDto.setPaymentPerShare(convertToBigDecimal(div.getDividendNet()));
            dividendDto.setCurrency(div.getDividendNet().getCurrency());
            dividendDto.setInterestIncome(convertToBigDecimal(div.getYieldValue()));

            dividendsDto.add(dividendDto);
        }

        return dividendsDto;
    }

    public BigDecimal getLastPrice(String figi) {
        var lastPrice = investApi.getMarketDataService().getLastPricesSync(List.of(figi)).get(0).getPrice();

        return convertToBigDecimal(lastPrice);
    }

    public List<BigDecimal> getLastPrices(List<String> figis) {
        var lastPrice = investApi.getMarketDataService().getLastPricesSync(figis);

        List<BigDecimal> lastPriceDecimal = new ArrayList<>(lastPrice.size());

        for (var i : lastPrice) {
            lastPriceDecimal.add(convertToBigDecimal(i.getPrice()));
        }

        return lastPriceDecimal;
    }

    /**
     * Метод по генерации ответа по покупке акции
     * @param shareToBuy - акция, которая будет куплена пользователем
     * @param postOrderResponse - ответ от tinkoff api о выставленной заявке/покупке
     * @return
     */
    private OrderResponse<StockDto> generateOrderResponse(Share shareToBuy, PostOrderResponse postOrderResponse) {
        StockDto stockDto = stockMapper.toDto(shareToBuy, getLastPrice(shareToBuy.getFigi()));

        return setOrderResponseFields(postOrderResponse, stockDto);
    }

    private OrderResponse<StockDto> generateOrderResponse(StockDto stockDto, PostOrderResponse postOrderResponse) {
        return setOrderResponseFields(postOrderResponse, stockDto);
    }

    private OrderResponse<StockDto> setOrderResponseFields(PostOrderResponse postOrderResponse, StockDto stockDto) {
        OrderResponse<StockDto> orderResponse = new OrderResponse<>();
        orderResponse.setOrderId(postOrderResponse.getOrderId());
        orderResponse.setExecutionStatus(postOrderResponse.getExecutionReportStatus().name());
        orderResponse.setLotRequested((int) postOrderResponse.getLotsRequested());
        orderResponse.setLotExecuted((int) postOrderResponse.getLotsExecuted());
        orderResponse.setInitialOrderPrice(MoneyParser.moneyValueToBigDecimal(postOrderResponse.getInitialOrderPrice()));
        orderResponse.setExecutedOrderPrice(MoneyParser.moneyValueToBigDecimal(postOrderResponse.getExecutedOrderPrice()));
        orderResponse.setTotalOrderPrice(MoneyParser.moneyValueToBigDecimal(postOrderResponse.getTotalOrderAmount()));
        orderResponse.setAsset(stockDto);
        return orderResponse;
    }

    /**
     * Получение цены акции по figi
     * для последущего выставления заявки на покупку
     *
     * @param price
     * @return Quotation (units - рубли, nanos - копейки)
     */
    private Quotation getPrice(BigDecimal price) {

        return Quotation.newBuilder()
                .setUnits(price != null ? price.longValue() : 0)
                .setNano(price != null ? price.remainder(BigDecimal.ONE)
                        .multiply(BigDecimal.valueOf(1000000000)).intValue() : 0)
                .build();
    }

    /**
     * Просмежуточное получение цены акции
     * в виде BigDecimal
     *
     * @param figi
     * @return
     */

    private BigDecimal getBigDecimalPrice(String figi) {
        Quotation shareLastPrice = investApi.getMarketDataService()
                .getLastPricesSync(List.of(figi)).get(0)
                .getPrice();
        Quotation minPriceIncrement = investApi.getInstrumentsService()
                .getInstrumentByFigiSync(figi)
                .getMinPriceIncrement();

        BigDecimal lastPrice
                = shareLastPrice.getUnits() == 0 && shareLastPrice.getNano() == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(shareLastPrice.getUnits()).add(BigDecimal.valueOf(shareLastPrice.getNano(), 9));
        BigDecimal minPrice
                = minPriceIncrement.getUnits() == 0 && minPriceIncrement.getNano() == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(minPriceIncrement.getUnits()).add(BigDecimal.valueOf(minPriceIncrement.getNano(), 9));

        return lastPrice.subtract(minPrice.multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)));
    }
}