package com.egen.confgiuration;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


public class SwaggerConfig {

    @Bean
    public Docket orderManagementApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Order Management API")
                .version("1.0")
                .description("API for Order Management Application")
                .contact(new Contact("Vaishnavi Manjunath", "https://github.com/vmanjunath13",
                        "vmanjunath@hawk.iit.edu"))
                .license("Apache License Version 2.0")
                .build();
    }
}
