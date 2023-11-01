package com.hmproject.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {


    @Bean
    @LoadBalanced           // -> will load balance + will use name of services from service-registry
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
