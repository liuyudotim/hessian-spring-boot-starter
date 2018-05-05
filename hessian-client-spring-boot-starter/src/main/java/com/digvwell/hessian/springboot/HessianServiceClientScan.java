package com.digvwell.hessian.springboot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HessianServiceClientScannerRegister.class)
@Repeatable(HessianServiceClientScans.class)
public @interface HessianServiceClientScan {
    String basePackage() default "";

    String serviceName() default "";
    String path() default "";
}
