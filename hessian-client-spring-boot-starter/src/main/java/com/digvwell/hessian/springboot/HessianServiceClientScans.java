package com.digvwell.hessian.springboot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HessianServiceClientScannerRegister.RepeatingRegister.class)
public @interface HessianServiceClientScans {
    HessianServiceClientScan[] value();
}
