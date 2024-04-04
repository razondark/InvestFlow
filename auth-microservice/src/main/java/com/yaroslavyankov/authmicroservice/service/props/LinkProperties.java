package com.yaroslavyankov.authmicroservice.service.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rest")
public class LinkProperties {

    private String userServiceLink;

}
