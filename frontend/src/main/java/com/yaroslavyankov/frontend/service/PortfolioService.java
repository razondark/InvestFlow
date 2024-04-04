package com.yaroslavyankov.frontend.service;


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

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final RestTemplate restTemplate;

    private final InvestLinkProperties investLinkProperties;

    @SneakyThrows
    public PortfolioResponse getPortfolio(String accountId, String accessToken) {

        PortfolioRequest portfolioRequest = new PortfolioRequest();
        portfolioRequest.setAccountId(accountId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PortfolioRequest> request = new HttpEntity<>(portfolioRequest, headers);


        ResponseEntity<PortfolioResponse> response = restTemplate
                .exchange(investLinkProperties.getPortfolioLink(), HttpMethod.POST, request, PortfolioResponse.class);

        return response.getBody();
    }
}
