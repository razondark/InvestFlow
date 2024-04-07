package com.mvp.investservice.web.controller;

import com.mvp.investservice.service.PortfolioService;
import com.mvp.investservice.web.dto.portfolio.PortfolioRequest;
import com.mvp.investservice.web.dto.portfolio.PortfolioResponse;
import com.mvp.investservice.web.dto.portfolio.PositionResponse;
import com.mvp.investservice.web.dto.portfolio.WithdrawMoney;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invest/portfolios")
@RequiredArgsConstructor
@Tag(name="OperationController", description="Работа с портфелем аккаунта")
public class PortfolioController {


    private final PortfolioService portfolioService;

    @Operation(
            summary = "Получение информации о портфеле",
            description = "Возвращает сущность информации о портфеле пользователя"
    )
    @PostMapping
    public PortfolioResponse getUserPortfolio(@Parameter(description = "Сущность запроса информации о портфеле пользователя")
                                                  @RequestBody PortfolioRequest portfolioRequest) {
        return portfolioService.getPortfolio(portfolioRequest);
    }

    @Operation(
            summary = "Получение информации о валютах в портфеле",
            description = "Возвращает сущность информации о валютах пользователя в портфеле"
    )
    @PostMapping("/withdraw")
    public List<WithdrawMoney> getWithdraw(@Parameter(description = "Сущность валют пользователя в портфеле")
                                               @RequestBody PortfolioRequest portfolioRequest) {
        return portfolioService.getWithdrawLimits(portfolioRequest.getAccountId());
    }

    @Operation(
            summary = "Получение информации об активах в портфеле",
            description = "Возвращает сущность информации об активах пользователя в портфеле"
    )
    @PostMapping("/positions")
    public List<PositionResponse> getUserPositions(@Parameter(description = "Сущность активов портфеля")
                                                       @RequestBody PortfolioRequest portfolioRequest) {
        return portfolioService.getPortfolioPositions(portfolioRequest);
    }
}
