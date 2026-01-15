package com.cy.kcat.workflow;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProcessTest {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    ProcessEngine processEngine;


    @Test
    public void test() {
        List<ProcessInstance> vocationRequest = runtimeService.createProcessInstanceQuery()
            .processDefinitionKey("vocationRequest")
            .list();


    }
}
