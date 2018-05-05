package com.digvwell.hessian.example.service;

import com.digvwell.hessian.springboot.HessianServiceClientScan;
import com.digvwell.hessian.springboot.HessianServiceClientScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@HessianServiceClientScans(
        @HessianServiceClientScan(serviceName = "springboot-hessian-service", basePackage = "com.digvwell.hessian.springboot.hessian.service", path = "/")
)
public class HessianServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessianServiceConsumerApplication.class, args);
    }


}
