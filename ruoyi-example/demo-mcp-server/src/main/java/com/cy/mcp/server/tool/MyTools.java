package com.cy.mcp.server.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MyTools{
    @Tool(description = "获取当前时间")
    public String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);

    }
}
