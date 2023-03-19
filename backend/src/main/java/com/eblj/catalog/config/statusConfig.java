package com.eblj.catalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class statusConfig {

    @Value("${environment_Message}")
    private String environment_Message;
    @Value("${environment}")
    private String environment;
    @Value("${database}")
    private String database;
    @Value("${database_name}")
    private String database_name;

    @Bean
    public void status(){
        System.out.println("[ ------------- " +environment_Message + " " + environment +" ----------- ]");
        System.out.println("[ ------------------ " +database + " "+ database_name +" ----------------- ]");
    }

}
