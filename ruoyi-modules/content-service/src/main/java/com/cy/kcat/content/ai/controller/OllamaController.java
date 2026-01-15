package com.cy.kcat.content.ai.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ollama")
public class OllamaController {
    @Autowired
    OllamaChatModel ollamaChatModel;

    @GetMapping("/chat")
    public Flux<String> getChat(@RequestParam("msg") String msg) {
    return null;
    }


}
