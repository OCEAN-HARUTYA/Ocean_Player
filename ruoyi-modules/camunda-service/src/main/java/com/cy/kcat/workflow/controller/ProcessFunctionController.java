package com.cy.kcat.workflow.controller;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProcessFunctionController {
    @Autowired
    RuntimeService runtimeService;

    @GetMapping("/vocation/process/start")
    public ProcessInstance  startProcessFunction(){
        //从已经部署的一个流程定义中获取key
        ProcessInstance vocationRequest = runtimeService.startProcessInstanceByKey("vocationRequest ");
        String id = vocationRequest.getId();
        String caseInstanceId = vocationRequest.getCaseInstanceId();
        return vocationRequest;

    }
}
