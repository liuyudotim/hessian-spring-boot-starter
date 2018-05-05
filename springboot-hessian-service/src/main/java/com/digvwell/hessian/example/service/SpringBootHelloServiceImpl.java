package com.digvwell.hessian.example.service;

import org.springframework.stereotype.Service;

/**
 * Created by liuyu on 2017/5/24.
 */
@Service
public class SpringBootHelloServiceImpl implements SpringBootHelloService {
    @Override
    public String hello(String name) {
        return "Hello," + name + "!";
    }
}
