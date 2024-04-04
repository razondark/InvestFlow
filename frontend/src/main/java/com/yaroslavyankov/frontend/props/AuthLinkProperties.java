package com.yaroslavyankov.frontend.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthLinkProperties {

    private String loginLink;

    private String registerLink;
}
