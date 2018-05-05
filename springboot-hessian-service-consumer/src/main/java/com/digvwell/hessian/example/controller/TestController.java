package com.digvwell.hessian.example.controller;

import com.digvwell.hessian.example.service.SpringBootHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuyu on 2017/5/26.
 */
@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private SpringBootHelloService helloService;

    @RequestMapping("hello")
    public String test(@RequestParam String name) throws Exception {
        return helloService.hello(name);
    }
}

