package im.liuyu.hessian.annotation;

import im.liuyu.hessian.springboot.HessianServiceClientScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repeatable(HessianServiceClientScans.class)
@Import(HessianServiceClientScannerRegistrar.class)
public @interface HessianServiceClientScan {
    String basePackage() default "";

    String serviceName() default "";

    String hostAndPort() default "";

    String path() default "";
}
