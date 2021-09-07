package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Value("db.schema")
    private String defaultSchema;

    public String getDefaultSchema(){
        return defaultSchema;
    }
}
