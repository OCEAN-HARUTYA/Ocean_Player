package com.cy.kcat.content.vod.config;

import com.cy.kcat.content.vod.properties.VodProperties;
import com.qcloud.vod.VodUploadClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.vod.v20180717.VodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VodConfig {

    @Autowired
    VodProperties vodProperties;

    @Bean
    public VodUploadClient vodConfigClient() {
        return new VodUploadClient(
            vodProperties.getSecretId(), vodProperties.getSecretKey()
        );
    }

    @Bean
    public VodClient vodClient() {
        Credential credential = new Credential(vodProperties.getSecretId(), vodProperties.getSecretKey());
        return new VodClient(credential, vodProperties.getRegion());

    }
}
