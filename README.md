# hessian-client-spring-boot-starter
##  How to use
### Add dependence

```xml
<dependency> 
  <groupId>im.liuyu.hessian</groupId>  
  <artifactId>hessian-client-spring-boot-starter</artifactId>  
  <version>1.0.0-SNAPSHOT</version> 
</dependency>
```

### Use @HessianServiceClientScan annotation

SpringBootApplication

```java
@HessianServiceClientScan(basePackage = "im.liuyu.hessian.service", serviceName = "HESSIAN-STARTER-SPRINGBOOT-HESSIAN-SERVICE")
@HessianServiceClientScan(basePackage = "im.liuyu.hessian.service2", serviceName = "HESSIAN-STARTER-SPRINGBOOT-HESSIAN-SERVICE2")
public class HessianServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessianServiceConsumerApplication.class, args);
    }
}
```