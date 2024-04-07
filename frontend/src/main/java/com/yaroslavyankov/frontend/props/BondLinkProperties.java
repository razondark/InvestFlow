package com.yaroslavyankov.frontend.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "bond")
public class BondLinkProperties {

    private String allBondsLink;

    private String buyBondLink;

    private String bondByFigiLink;

    private String saleBondLink;
}
