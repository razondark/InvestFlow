package com.yaroslavyankov.frontend.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "invest")
public class InvestLinkProperties {

    private String portfolioLink;

    private String allOperationsLink;

    private String accountBalanceLink;
}
