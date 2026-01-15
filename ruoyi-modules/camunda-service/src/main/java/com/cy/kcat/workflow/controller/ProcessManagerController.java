package com.cy.kcat.workflow.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.dromara.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/workflow")
public class ProcessManagerController {

    @Autowired
    RuntimeService runtimeService;

    /**
     * 启动流程
     * @param processKey
     * @return
     */
    @GetMapping("/process/start")
    public R start(@RequestParam("processKey") String processKey) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey);
        return R.ok("success", instance.getId());
    }
}
