package com.mvp.investservice.service;

import com.mvp.investservice.web.dto.portfolio.PortfolioRequest;
import com.mvp.investservice.web.dto.portfolio.PortfolioResponse;
import com.mvp.investservice.web.dto.portfolio.PositionResponse;
import com.mvp.investservice.web.dto.portfolio.WithdrawMoney;

import java.util.List;

public interface PortfolioService {

    List<PositionResponse> getPortfolioPositions(PortfolioRequest portfolioRequest);

    PortfolioResponse getPortfolio(PortfolioRequest portfolioRequest);

    List<WithdrawMoney> getWithdrawLimits(String accountId);
}
