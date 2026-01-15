package com.cy.kcat.feign;

import com.cy.kcat.fallback.CamundaFallbackFactory;
import org.dromara.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "camunda-service",
    path = "/workflow",
    contextId = "camundaFeignClient",
    fallbackFactory = CamundaFallbackFactory.class)
public interface CamundaFeignClient {
    @GetMapping("/process/start")
    public R start(@RequestParam("processKey") String processKey);
}
