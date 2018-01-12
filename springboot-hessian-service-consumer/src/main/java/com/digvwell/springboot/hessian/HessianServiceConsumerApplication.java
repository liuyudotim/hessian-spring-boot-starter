package com.digvwell.springboot.hessian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liuyu on 2017/7/5.
 */
@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@ComponentScan("net.yunling.utd.springboot.hessian")
public class HessianServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessianServiceConsumerApplication.class, args);
    }


}
