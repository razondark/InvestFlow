package com.example.reportservice.controller;

import com.example.reportservice.model.dto.AccountDto;
import com.example.reportservice.model.OperationResponse;
import com.example.reportservice.tinkoff.accountInfo.Account;
import com.example.reportservice.tinkoff.reports.OperationReport;
import com.example.reportservice.tinkoff.reports.StockReport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.piapi.core.InvestApi;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/report")
@AllArgsConstructor
public class OperationController {
    private final RestTemplate restTemplate;

    @PostMapping
    public OperationResponse getOperations(@RequestBody AccountDto accountDto) throws IOException {
        OperationResponse response = restTemplate
                .postForObject("http://localhost:9098/api/v1/invest/operations", accountDto,OperationResponse.class);
        operations(response, accountDto);
        return response;
    }
    public void operations(OperationResponse operationResponse, AccountDto accountDto) throws IOException {
//        var sandboxApi = InvestApi
//                .createSandbox("t.VqnElEfrPsAXQfuhO8JgcnOUhpz9dI3dNq6yFntZaSuRtU7eKgBNusIpTIifs14jsPfI5hRl47JXsByKqWGMFg");
//        var account = accountDto.getInvestAccountId();
//        var positions = sandboxApi.getOperationsService().getPositionsSync(account);
//        ArrayList<String> stoncksPositionList = Account.getStocksPositions(positions);
//        StockReport stockReport = new StockReport(stoncksPositionList);
        OperationReport operationReport = new OperationReport(operationResponse);
    }
}
