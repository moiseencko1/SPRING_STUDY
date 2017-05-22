package com.example.springstudy;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class SpringStudyApplication {

    @Autowired
    private EurekaClient eurekaClient;

    @Bean
    public VersionFilter versionFilter() {
        return new VersionFilter(eurekaClient);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }
}

