package com.cy.kcat.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GatewayTest {
    // vocation:1:ef7b8404-d41b-11f0-a18a-005056c00008
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @Test
    public void test() {
        //1.启动流程
        String id = "vocation:1:ef7b8404-d41b-11f0-a18a-005056c00008";
        ProcessInstance instance = runtimeService.startProcessInstanceById(id);
        System.out.println("实例id" + instance.getId());
    }
}
