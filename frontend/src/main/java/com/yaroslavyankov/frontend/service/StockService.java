package com.yaroslavyankov.frontend.service;

import com.vaadin.flow.component.notification.Notification;
import com.yaroslavyankov.frontend.dto.*;
import com.yaroslavyankov.frontend.props.StockLinkProperties;
import com.yaroslavyankov.frontend.service.exception.ExceptionBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final RestTemplate restTemplate;

    private final StockLinkProperties stockLinkProperties;

    @SneakyThrows
    public List<StockDto> getStockByFigi(String figi, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PortfolioRequest> request = new HttpEntity<>(headers);

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stockLinkProperties.getStockByFigiLink())
                .queryParam("name", figi);

        ResponseEntity<List<StockDto>> response = restTemplate.exchange(
                componentsBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<StockDto>>() {}
        );

        return response.getBody();
    }

    @SneakyThrows
    public List<StockDto> getStocks(String accessToken) {
        int page = 1;
        int count = 300;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PortfolioRequest> request = new HttpEntity<>(headers);

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                .fromHttpUrl(stockLinkProperties.getAllStocksLink())
                .queryParam("page", page)
                .queryParam("count", count);

        ResponseEntity<List<StockDto>> response = restTemplate.exchange(
                componentsBuilder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<StockDto>>() {}
        );

        return response.getBody();
    }

    //@SneakyThrows
    public OrderResponse<StockDto> buyStock(PurchaseRequest purchase, String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchase, headers);

        try {
            ResponseEntity<OrderResponse> response = restTemplate.exchange(
                    stockLinkProperties.getBuyStockLink(),
                    HttpMethod.POST,
                    request,
                    OrderResponse.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            //Notification.show("Невозможно купить акцию", 3000, Notification.Position.MIDDLE);
            return null;
        }
    }

    public OrderResponse<StockDto> saleStock(SaleRequest sale, String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<SaleRequest> request = new HttpEntity<>(sale, headers);

        try {
            ResponseEntity<OrderResponse> response = restTemplate.exchange(
                    stockLinkProperties.getSaleStockLink(),
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
