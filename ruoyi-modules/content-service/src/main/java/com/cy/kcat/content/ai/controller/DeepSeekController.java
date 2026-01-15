package com.cy.kcat.content.ai.controller;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ds")
public class DeepSeekController {

    private final DeepSeekChatModel chatModel;

    @Autowired
    public DeepSeekController(DeepSeekChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("msg") String message) {
    //1.用户消息
        UserMessage userMessage = new UserMessage(message);

        String systemText = """
         你是一个智能助手
         你的名字是 {name}，你是一个网络喷子，言语极其犀利
          """;

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        //系统提示词
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", "siri"));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        ChatResponse call = chatModel.call(prompt);
        return call.getResult().getOutput().getText();
    }


    @GetMapping(value = "/chat2",produces = "text/html;charset=UTF-8")
    public  Flux<String> chat2(@RequestParam("msg") String message) {
        //1.用户消息
        UserMessage userMessage = new UserMessage(message);

        String systemText = """
         你是一个智能助手
         你的名字是 {name}, 你是一个网络喷子，言语极其犀利
          """;

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        //系统提示词
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", "siri"));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        Flux<String> stream = chatModel.stream(systemMessage, userMessage);

        return stream;
    }
}
