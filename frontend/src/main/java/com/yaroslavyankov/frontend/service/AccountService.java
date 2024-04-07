package com.yaroslavyankov.frontend.service;

import com.yaroslavyankov.frontend.BalanceResponse;
import com.yaroslavyankov.frontend.dto.BalanceRequest;
import com.yaroslavyankov.frontend.dto.PortfolioRequest;
import com.yaroslavyankov.frontend.dto.PortfolioResponse;
import com.yaroslavyankov.frontend.props.InvestLinkProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final RestTemplate restTemplate;

    private final InvestLinkProperties investLinkProperties;

    @SneakyThrows
    public BalanceResponse getBalance(String accountId, String accessToken) {

        var balanceRequest = new BalanceRequest();
        balanceRequest.setAccountId(accountId);
        balanceRequest.setMoneyToPay(BigDecimal.ZERO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<BalanceRequest> request = new HttpEntity<>(balanceRequest, headers);


        ResponseEntity<BalanceResponse> response = restTemplate
                .exchange(investLinkProperties.getAccountBalanceLink(), HttpMethod.PUT, request, BalanceResponse.class);

        return response.getBody();
    }

    @SneakyThrows
    public BalanceResponse addBalance(String accountId, BigDecimal addedMoney, String accessToken) {

        var balanceRequest = new BalanceRequest();
        balanceRequest.setAccountId(accountId);
        balanceRequest.setMoneyToPay(addedMoney);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<BalanceRequest> request = new HttpEntity<>(balanceRequest, headers);


        ResponseEntity<BalanceResponse> response = restTemplate
                .exchange(investLinkProperties.getAccountBalanceLink(), HttpMethod.PUT, request, BalanceResponse.class);

        return response.getBody();
    }
}
