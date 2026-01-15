package com.cy.mcp.server.config;

import com.cy.mcp.server.tool.MyTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {
    /**
     * 利用ToolCallbackProvider 暴露所有工具
     * @return
     */
    @Bean
    public ToolCallbackProvider toolCallbackProvider() {
        MethodToolCallbackProvider build = MethodToolCallbackProvider.
            builder().toolObjects(new MyTools()).build();
        return build;
    }
}
