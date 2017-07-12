package com.yuisme.springboot.hessian;

import com.yuisme.springboot.hessian.annotations.HessianService;

/**
 * Created by liuyu on 2017/5/24.
 */
@HessianService(SpringBootHelloService.class)
public class SpringBootHelloServiceImpl implements SpringBootHelloService {
    @Override
    public String hello(String name) {
        return "Hello," + name + "!";
    }
}
