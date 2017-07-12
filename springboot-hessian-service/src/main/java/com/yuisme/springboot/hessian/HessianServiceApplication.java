package com.yuisme.springboot.hessian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by liuyu on 2017/7/5.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class HessianServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HessianServiceApplication.class, args);
    }
}
