package com.digvwell.springboot.hessian;

import com.digvwell.springboot.hessian.annotations.HessianClient;

/**
 * Created by liuyu on 2017/5/24.
 */
@HessianClient("springboot-hessian-service")
public interface SpringBootHelloService {
    public String hello(String name);
}
