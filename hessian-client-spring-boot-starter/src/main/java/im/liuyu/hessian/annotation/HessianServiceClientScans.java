package im.liuyu.hessian.annotation;

import im.liuyu.hessian.springboot.HessianServiceClientScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HessianServiceClientScannerRegistrar.class)
public @interface HessianServiceClientScans {
    HessianServiceClientScan[] value();
}
