package com.yaroslavyankov.frontend.service;

import com.yaroslavyankov.frontend.dto.*;
import com.yaroslavyankov.frontend.props.BondLinkProperties;
import com.yaroslavyankov.frontend.props.StockLinkProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BondService {

    private final RestTemplate restTemplate;

    private final BondLinkProperties bondLinkProperties;

    @SneakyThrows
    public List<BondDto> getBondByFigi(String figi, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PortfolioRequest> request = new HttpEntity<>(headers);

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(bondLinkProperties.getBondByFigiLink())
                .queryParam("name", figi);

        ResponseEntity<List<BondDto>> response = restTemplate.exchange(
                componentsBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<BondDto>>() {}
        );

        return response.getBody();
    }

    @SneakyThrows
    public List<BondDto> getBonds(String accessToken) {
        int page = 1;
        int count = 300;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PortfolioRequest> request = new HttpEntity<>(headers);

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(bondLinkProperties.getAllBondsLink())
                .queryParam("page", page)
                .queryParam("count", count);

        ResponseEntity<List<BondDto>> response = restTemplate.exchange(
                componentsBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<BondDto>>() {}
        );

        return response.getBody();
    }

    public OrderResponse<BondDto> buyBond(PurchaseRequest purchase, String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchase, headers);

        try {
            ResponseEntity<OrderResponse> response = restTemplate.exchange(
                    bondLinkProperties.getBuyBondLink(),
                    HttpMethod.POST,
                    request,
                    OrderResponse.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            //Notification.show("Невозможно купить облигацию", 3000, Notification.Position.MIDDLE);
            return null;
        }
    }

    public OrderResponse<BondDto> saleBond(SaleRequest sale, String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<SaleRequest> request = new HttpEntity<>(sale, headers);

        try {
            ResponseEntity<OrderResponse> response = restTemplate.exchange(
                    bondLinkProperties.getSaleBondLink(),
                    HttpMethod.POST,
                    request,
                    OrderResponse.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            //Notification.show("Невозможно продать акцию", 3000, Notification.Position.MIDDLE);
            return null;
        }
    }
}
