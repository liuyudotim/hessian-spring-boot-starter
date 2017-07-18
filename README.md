# hessian-spring-boot-starter
##  How to use
### Add dependence

```xml
<dependency>
	<groupId>com.yuisme.springboot.hessian</groupId>
	<artifactId>hessian-client-spring-boot-starter</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

### Use @HessianClient annotation
Hessian client interface

```java
@HessianClient("springboot-hessian-service")
public interface SpringBootHelloService {
    public String hello(String name);
}
```
>`springboot-hessian-service`: A registered hessian service

SpringBootApplication

```java
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.yuisme.springboot.hessian")
public class HessianServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessianServiceConsumerApplication.class, args);
    }
}
```
>`com.yuisme.springboot.hessian`:The package hessian clients are located

application.yml

```yml
spring:
  application:
    name: springboot-hessian-service-consumer
server:
  port: 9004
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```
