package com.yaroslavyankov.frontend.service;

import com.yaroslavyankov.frontend.dto.OperationRequest;
import com.yaroslavyankov.frontend.dto.OperationResponse;
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
public class OperationService {
    private final RestTemplate restTemplate;

    private final InvestLinkProperties investLinkProperties;

    @SneakyThrows
    public OperationResponse getOperations(String accountId, String accessToken) {

        var operationRequest = new OperationRequest();
        operationRequest.setInvestAccountId(accountId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<OperationRequest> request = new HttpEntity<>(operationRequest, headers);


        ResponseEntity<OperationResponse> response = restTemplate
                .exchange(investLinkProperties.getAllOperationsLink(), HttpMethod.POST, request, OperationResponse.class);

        return response.getBody();
    }
}
