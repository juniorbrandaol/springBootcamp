package com.eblj.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))//.basePackage("com.eblj.catalog.rest.resources"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
               .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder()

                .title("Sistema de Vendas")
                .description("Api do projeto de vendas")
                .license("GNU GENERAL PUBLIC LICENSE, Version 1")
                .version("1.0")
                .contact(contact())
                .build();

    }
    private Contact contact(){
        return  new Contact(
                "Edilson Brandão",
                "https://github.com/juniorbrandaol",
                "edilson_brandaojunior@hotmail.com");
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return  SecurityContext.builder()
                .securityReferences(defautAuth())
                .operationSelector(operationContext -> true)
                .build();
    }

    private List<SecurityReference> defautAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global","accessEverything"
        );
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0]= authorizationScope;
        SecurityReference reference = new SecurityReference("JWT",scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }

}
