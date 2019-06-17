package hessian;

import im.liuyu.hessian.annotation.HessianServiceClientScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by liuyu on 2017/7/5.
 */
@EnableDiscoveryClient
@SpringBootApplication
@HessianServiceClientScan(basePackage = "im.liuyu.hessian.service", serviceName = "HESSIAN-STARTER-SPRINGBOOT-HESSIAN-SERVICE")
@HessianServiceClientScan(basePackage = "im.liuyu.hessian.service2", serviceName = "HESSIAN-STARTER-SPRINGBOOT-HESSIAN-SERVICE2")
public class HessianServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessianServiceConsumerApplication.class, args);
    }
}
