package com.digvwell.springboot.hessian;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HessianServiceClientScannerRegistrar.RepeatingRegistrar.class)
public @interface HessianServiceClientScans {
    HessianServiceClientScan[] value();
}
