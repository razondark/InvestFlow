package com.mvp.investservice.service.impl;

import com.mvp.investservice.domain.exception.*;
import com.mvp.investservice.service.BondService;
import com.mvp.investservice.service.cache.CacheService;
import com.mvp.investservice.util.MoneyParser;
import com.mvp.investservice.util.SectorBondUtil;
import com.mvp.investservice.web.dto.OrderResponse;
import com.mvp.investservice.web.dto.PurchaseDto;
import com.mvp.investservice.web.dto.SaleDto;
import com.mvp.investservice.web.dto.bond.BondDto;
import com.mvp.investservice.web.dto.bond.CouponDto;
import com.mvp.investservice.web.dto.bond.CouponResponse;
import com.mvp.investservice.web.dto.portfolio.PortfolioRequest;
import com.mvp.investservice.web.dto.stock.DividendDto;
import com.mvp.investservice.web.mapper.BondMapper;
import lombok.RequiredArgsConstructor;
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
public class BondServiceImpl implements BondService {
    private final CacheService cacheService;

    private final InvestApi investApi;
    private final BondMapper bondMapper;
    private final PortfolioServiceImpl portfolioService;
    private final AccountServiceImpl accountService;

    @Override
    public List<BondDto> getBondsByName(String name) {
        var bondsInfo = investApi.getInstrumentsService().findInstrumentSync(name)
                .stream().filter(b -> b.getInstrumentType().equalsIgnoreCase("bond")).toList();
        if (bondsInfo.isEmpty()) {
            throw new ResourceNotFoundException("Не удалось найти облигацию: " + name);
        }

        var tradableBonds = cacheService.getTradableBondsSync(investApi);
        List<String> bondFigis = new ArrayList<>(bondsInfo.size());
        for (var bond : bondsInfo) {
            if (!tradableBonds.stream().filter(b -> b.getFigi().equalsIgnoreCase(bond.getFigi())).findFirst().isEmpty()) {
                bondFigis.add(bond.getFigi());
            }
        }

        List<Bond> bonds = new ArrayList<>();
        for (var figi : bondFigis) {
            bonds.add(investApi.getInstrumentsService().getBondByFigiSync(figi));
        }

        return bondMapper.toDto(bonds, getLastPrices(bondFigis));
    }

    @Override
    public List<BondDto> getBonds(Integer page, Integer count) {
        if (page < 1) {
            page = 1;
        }
        if (count < 1) {
            count = 10;
        }

        var tradableBonds = cacheService.getTradableBondsSync(investApi).subList((page - 1) * count, count);

        List<BondDto> bonds = new ArrayList<>();
        for (var bond : tradableBonds) {
            if (bond.getCurrency().equals("rub")) {
                bonds.add(bondMapper.toDto(bond, getLastPrice(bond.getFigi())));
            }
        }

        return bonds;
    }

    @Override
    public List<BondDto> getBondsBySector(String sectorName, Integer count) {
        if (count <= 0) {
            count = 10;
        }

        var sector = SectorBondUtil.valueOfRussianName(sectorName);
        var tradableBonds = cacheService.getTradableBondsSync(investApi);

        List<Bond> bondsBySector = new ArrayList<>();

        for (var i = 0; i < tradableBonds.size() && bondsBySector.size() < count; i++) {
            var bond = tradableBonds.get(i);
            if (bond.getSector().equalsIgnoreCase(sector)) {
                bondsBySector.add(bond);
            }
        }

        List<BondDto> bonds = new ArrayList<>();
        for (var bond : bondsBySector) {
            bonds.add(bondMapper.toDto(bond, getLastPrice(bond.getFigi())));
        }

        return bonds;
    }

    @Override
    public OrderResponse<BondDto> buyBond(PurchaseDto purchaseDto) throws InsufficientFundsException {
        Bond purchasedBond = null;
        try {
            purchasedBond = investApi.getInstrumentsService().getBondByFigiSync(purchaseDto.getFigi());
        } catch (Exception e) {
            throw new AssetNotFoundException(e.getMessage());
        }

        if (purchasedBond.getBuyAvailableFlag() && purchasedBond.getApiTradeAvailableFlag()) {
            var figi = purchasedBond.getFigi();
            var price = getBigDecimalPrice(figi);
            var resultPrice = getPrice(price);

            var balance = accountService.getBalance(purchaseDto.getAccountId());
            if (balance.compareTo(price) <= 0) {
                throw new InsufficientFundsException(price, balance);
            }

            try {
                var postOrderResponse = investApi.getOrdersService()
                        .postOrderSync(figi, purchaseDto.getLot(), resultPrice, OrderDirection.ORDER_DIRECTION_BUY,
                                purchaseDto.getAccountId(), OrderType.valueOf(purchaseDto.getOrderType().name()),
                                UUID.randomUUID().toString());

                return generateOrderResponse(purchasedBond, postOrderResponse);
            } catch (Exception e) {
                throw new BuyUnavailableException(e.getMessage());
            }
        } else {
            throw new BuyUnavailableException("В данный момент невозможно купить данную облигацию");
        }
    }



    @Override
    public OrderResponse<BondDto> saleBond(SaleDto saleDto) {
        BondDto saleBond = null;
        try {
            var portfolio = new PortfolioRequest();
            portfolio.setAccountId(saleDto.getAccountId());

            var positions = portfolioService.getPortfolioPositions(portfolio);
            if (positions.isEmpty()) {
                throw new AssetNotFoundException("В портфеле отсутсвуют ценные бумаги");
            }

            for (var asset : portfolioService.getPortfolioPositions(portfolio)) {
                if (asset.getAsset() instanceof BondDto) {
                    if (((BondDto)asset.getAsset()).getFigi().equalsIgnoreCase(saleDto.getFigi())) {
                        saleBond = (BondDto)asset.getAsset();
                        break;
                    }
                }
            }

            if (saleBond == null) {
                throw new AssetNotFoundException("В портфеле отсутсвует выбранная облигация");
            }

            var saleResponse = investApi.getOrdersService()
                    .postOrderSync(saleBond.getFigi(),
                                    saleDto.getLot(),
                                    getPrice(getBigDecimalPrice(saleBond.getFigi())),
                                    OrderDirection.ORDER_DIRECTION_SELL,
                                    saleDto.getAccountId(),
                                    OrderType.valueOf(saleDto.getOrderType().name()),
                                    UUID.randomUUID().toString());

            return generateOrderResponse(saleBond, saleResponse);

        } catch (Exception e) {
            throw new AssetNotFoundException(e.getMessage());
        }
    }

    @Override
    public CouponResponse getCoupons(String figi) {
        if (investApi.getInstrumentsService().findInstrumentSync(figi)
                .stream().filter(s -> s.getInstrumentType().equalsIgnoreCase("bond")
                        && s.getFigi().equalsIgnoreCase(figi)).toList().isEmpty()) {
            throw new AssetNotFoundException("Облигация не найдена");
        }

        var bond = investApi.getInstrumentsService().getBondByFigiSync(figi);

        var couponsAscDateAll = investApi.getInstrumentsService().getBondCouponsSync(figi, Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(9_000_000_000L));

        if (couponsAscDateAll.isEmpty()) {
            throw new YieldNotFoundException("Купоны по облигации отстуствуют");
        }

        var currentCouponsCount = investApi.getInstrumentsService().getBondCouponsSync(figi, Instant.ofEpochSecond(0),
                Instant.now()).stream().count();

        var couponsDescDate = new ArrayList<>(couponsAscDateAll);
        Collections.reverse(couponsDescDate);

        var nextPaymentDate = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(couponsAscDateAll.get((int)currentCouponsCount)
                        .getCouponDate().getSeconds()), ZoneId.systemDefault());

        return new CouponResponse(figi,
                nextPaymentDate,
                currentCouponsCount,
                couponsDescDate.stream().count(),
                generateCouponsDto(couponsDescDate, currentCouponsCount, bond));
    }

    private List<CouponDto> generateCouponsDto(List<Coupon> coupons, Long countCouponsNow, Bond bond) {
        var couponsDto = new ArrayList<CouponDto>();

        for (var coupon : coupons) {
            var couponDto = new CouponDto();

            couponDto.setCouponDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(coupon.getCouponDate().getSeconds()),
                    ZoneId.systemDefault()));
            couponDto.setAccumulatedCouponIncome(convertToBigDecimal(bond.getAciValue()));
            couponDto.setCouponNumber(coupon.getCouponNumber());
            couponDto.setPayment(convertToBigDecimal(coupon.getPayOneBond()));
            couponDto.setCurrency(coupon.getPayOneBond().getCurrency());

            var percent = convertToBigDecimal(coupon.getPayOneBond())
                            .multiply(BigDecimal.valueOf(bond.getCouponQuantityPerYear())
                            .multiply(BigDecimal.valueOf(100)))
                    .divide(convertToBigDecimal(bond.getNominal()));
            couponDto.setInterestIncome(percent);

            couponsDto.add(couponDto);
        }

        return couponsDto;
    }

    public BigDecimal getLastPrice(String figi) {
        var lastPrice = investApi.getMarketDataService().getLastPricesSync(List.of(figi)).get(0).getPrice();

        return convertToBigDecimal(lastPrice).multiply(BigDecimal.valueOf(10)); // ????
    }

    public List<BigDecimal> getLastPrices(List<String> figis) {
        var lastPrice = investApi.getMarketDataService().getLastPricesSync(figis);

        List<BigDecimal> lastPriceDecimal = new ArrayList<>(lastPrice.size());

        for (var i : lastPrice) {
            lastPriceDecimal.add(convertToBigDecimal(i.getPrice()).multiply(BigDecimal.valueOf(10)));
        }

        return lastPriceDecimal;
    }

    /**
     * Метод по генерации ответа по покупке акции
     * @param bond - акция, которая будет куплена пользователем
     * @param postOrderResponse - ответ от tinkoff api о выставленной заявке/покупке
     * @return
     */
    private OrderResponse<BondDto> generateOrderResponse(Bond bond, PostOrderResponse postOrderResponse) {
        BondDto bondDto = bondMapper.toDto(bond, getLastPrice(bond.getFigi()));

        return setOrderResponseFields(postOrderResponse, bondDto);
    }

    private OrderResponse<BondDto> generateOrderResponse(BondDto bond, PostOrderResponse postOrderResponse) {
        return setOrderResponseFields(postOrderResponse, bond);
    }

    private OrderResponse<BondDto> setOrderResponseFields(PostOrderResponse postOrderResponse, BondDto bondDto) {
        OrderResponse<BondDto> orderResponse = new OrderResponse<>();
        orderResponse.setOrderId(postOrderResponse.getOrderId());
        orderResponse.setExecutionStatus(postOrderResponse.getExecutionReportStatus().name());
        orderResponse.setLotRequested((int) postOrderResponse.getLotsRequested());
        orderResponse.setLotExecuted((int) postOrderResponse.getLotsExecuted());
        orderResponse.setInitialOrderPrice(MoneyParser.moneyValueToBigDecimal(postOrderResponse.getInitialOrderPrice()));
        orderResponse.setExecutedOrderPrice(MoneyParser.moneyValueToBigDecimal(postOrderResponse.getExecutedOrderPrice()));
        orderResponse.setTotalOrderPrice(MoneyParser.moneyValueToBigDecimal(postOrderResponse.getTotalOrderAmount()));
        orderResponse.setAsset(bondDto);
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
     * @return lastPrice
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
