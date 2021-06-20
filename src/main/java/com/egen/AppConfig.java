package com.egen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class AppConfig extends WebMvcConfigurationSupport {
	//Override addCorsMappings Method to allow cross origin request to API

    protected void addCorsMappings(CorsRegistry corsRegistry) {
        super.addCorsMappings(corsRegistry);
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AppConfig.class);
        springApplication.run();
    }
}
