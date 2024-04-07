package com.yaroslavyankov.frontend.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "stock")
public class StockLinkProperties {
    private String allStocksLink;

    private String buyStockLink;

    private String stockByFigiLink;

    private String saleStockLink;
}
