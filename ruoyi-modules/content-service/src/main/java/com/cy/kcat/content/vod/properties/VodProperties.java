package com.cy.kcat.content.vod.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "vod")
@Component
public class VodProperties {
    private String secretId;
    private String secretKey;
    private Long subAppId;
    private String region;
}
