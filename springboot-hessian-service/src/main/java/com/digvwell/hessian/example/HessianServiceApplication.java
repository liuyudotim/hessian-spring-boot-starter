package com.digvwell.hessian.example;

import com.digvwell.hessian.example.service.SpringBootHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;

/**
 * Created by liuyu on 2017/7/5.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class HessianServiceApplication {
    @Autowired
    private SpringBootHelloService helloService;

    @Bean("/SpringBootHelloService")
    public HessianServiceExporter getSpringBootHelloService() {
        HessianServiceExporter serviceExporter = new HessianServiceExporter();
        serviceExporter.setService(helloService);
        serviceExporter.setServiceInterface(SpringBootHelloService.class);
        return serviceExporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(HessianServiceApplication.class, args);
    }
}
