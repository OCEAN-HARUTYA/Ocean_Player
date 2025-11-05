package com.cy.kcat.content.minio.config;


import com.cy.kcat.content.minio.properties.MyMinioProperties;
import io.github.linpeilie.annotations.AutoMappings;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMinioConfig {

    @Autowired
    MyMinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {

        MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint()).
            credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
            .build();
        return minioClient;
    }
}
