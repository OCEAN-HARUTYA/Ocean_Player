package com.cy.kcat.fallback;

import com.cy.kcat.feign.CamundaFeignClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CamundaFallbackFactory implements FallbackFactory<CamundaFeignClient> {

    @Override
    public CamundaFeignClient create(Throwable cause) {
        return null;
    }
}
