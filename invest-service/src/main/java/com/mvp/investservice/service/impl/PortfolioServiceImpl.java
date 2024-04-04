package com.mvp.investservice.service.impl;

import com.mvp.investservice.domain.exception.CannotProceedApiRequestException;
import com.mvp.investservice.domain.exception.ResourceNotFoundException;
import com.mvp.investservice.service.PortfolioService;
import com.mvp.investservice.web.dto.Asset;
import com.mvp.investservice.web.dto.portfolio.PortfolioRequest;
import com.mvp.investservice.web.dto.portfolio.PortfolioResponse;
import com.mvp.investservice.web.dto.portfolio.PositionResponse;
import com.mvp.investservice.web.dto.portfolio.WithdrawMoney;
import com.mvp.investservice.web.mapper.BondMapper;
import com.mvp.investservice.web.mapper.CurrencyMapper;
import com.mvp.investservice.web.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Money;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;
import ru.tinkoff.piapi.core.models.WithdrawLimits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mvp.investservice.util.MoneyParser.convertToBigDecimal;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final InvestApi investApi;

    private final StockMapper stockMapper;

    private final BondMapper bondMapper;

    private final CurrencyMapper currencyMapper;

    @Override
    public List<PositionResponse> getPortfolioPositions(PortfolioRequest portfolioRequest) {
        return this.getPortfolio(portfolioRequest).getPositions();
    }

    @Override
    public PortfolioResponse getPortfolio(PortfolioRequest portfolioRequest) {
        try {
            Portfolio portfolio = investApi.getOperationsService()
                    .getPortfolioSync(portfolioRequest.getAccountId());

            List<Position> positions = portfolio.getPositions();

            List<PositionResponse> positionResponses = getPositionResponses(positions);

            return getPortfolioResponse(portfolioRequest, portfolio, positionResponses);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<WithdrawMoney> getWithdrawLimits(String accountId) {
        try {
            WithdrawLimits withdrawLimits = investApi.getOperationsService()
                    .getWithdrawLimitsSync(accountId);
            List<Money> moneyValues = withdrawLimits.getMoney();

            return getWithdrawMonies(moneyValues);
        } catch (Exception e) {
            throw new CannotProceedApiRequestException(e.getMessage());
        }
    }

    private List<WithdrawMoney> getWithdrawMonies(List<Money> moneyValues) {
        List<WithdrawMoney> withdrawMonies = new ArrayList<>();
        for (Money money : moneyValues) {
            WithdrawMoney withdrawMoney = new WithdrawMoney();
            withdrawMoney.setCurrency(money.getCurrency());
            withdrawMoney.setAmount(money.getValue());

            withdrawMonies.add(withdrawMoney);
        }
        return withdrawMonies;
    }

    private PortfolioResponse getPortfolioResponse(PortfolioRequest portfolioRequest, Portfolio portfolio, List<PositionResponse> positionResponses) {
        PortfolioResponse portfolioResponse = new PortfolioResponse();

        portfolioResponse.setAccountId(portfolioRequest.getAccountId());
        portfolioResponse.setTotalAmountCurrencies(portfolio.getTotalAmountCurrencies().getValue());
        portfolioResponse.setTotalAmountBonds(portfolio.getTotalAmountBonds().getValue());
        portfolioResponse.setTotalAmountShares(portfolio.getTotalAmountShares().getValue());
        portfolioResponse.setTotalAmountPortfolio(portfolio.getTotalAmountPortfolio().getValue());
        portfolioResponse.setExpectedYield(portfolio.getExpectedYield());
        portfolioResponse.setPositions(positionResponses);

        return portfolioResponse;
    }

    private List<PositionResponse> getPositionResponses(List<Position> positions) {
        List<PositionResponse> positionResponses = new ArrayList<>();

        for (Position position : positions) {
            Asset asset = getAsset(position);

            PositionResponse positionResponse = getPositionResponse(position, asset);

            positionResponses.add(positionResponse);
        }
        return positionResponses;
    }

    private Asset getAsset(Position position) {
        Asset asset = null;
        switch (position.getInstrumentType()) {
            case "share" -> {
                Share share = investApi.getInstrumentsService()
                        .getShareByFigiSync(position.getFigi());
                asset = stockMapper.toDto(share, getLastPrice(share.getFigi()));
            }
            case "bond" -> {
                Bond bond = investApi.getInstrumentsService()
                        .getBondByFigiSync(position.getFigi());
                asset = bondMapper.toDto(bond, getLastPrice(bond.getFigi()).multiply(BigDecimal.TEN)); // TODO: change?
            }
            case "currency" -> {
                Currency currency = investApi.getInstrumentsService()
                        .getCurrencyByFigiSync(position.getFigi());
                asset = currencyMapper.toDto(currency);
            }
        }
        return asset;
    }

    public BigDecimal getLastPrice(String figi) {
        var lastPrice = investApi.getMarketDataService().getLastPricesSync(List.of(figi)).get(0).getPrice();

        return convertToBigDecimal(lastPrice);
    }

    private PositionResponse getPositionResponse(Position position, Asset asset) {
        PositionResponse positionResponse = new PositionResponse();

        positionResponse.setAsset(asset);
        positionResponse.setAveragePositionPrice(position.getAveragePositionPrice().getValue());
        positionResponse.setCurrency(position.getCurrentPrice().getCurrency());
        positionResponse.setExpectedYield(position.getExpectedYield());
        positionResponse.setCurrentPrice(position.getCurrentPrice().getValue());
        positionResponse.setInstrumentType(position.getInstrumentType());
        positionResponse.setQuantity(position.getQuantity().intValue());
        positionResponse.setTotalPrice(position.getCurrentPrice().getValue().multiply(BigDecimal.valueOf(position.getQuantity().intValue())));

        return positionResponse;
    }
}
