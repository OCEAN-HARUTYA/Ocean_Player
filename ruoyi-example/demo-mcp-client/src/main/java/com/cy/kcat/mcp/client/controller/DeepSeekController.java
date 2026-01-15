package com.cy.kcat.mcp.client.controller;

import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DeepSeekController {


    @Autowired
    DeepSeekChatModel model;

    public DeepSeekController(){

    }
    public Flux<String> chat(@RequestParam String message) {
        return model.stream(message);

    }
}
