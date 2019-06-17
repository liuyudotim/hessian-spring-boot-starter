package hessian;

import im.liuyu.hessian.service.HelloService;
import im.liuyu.hessian.service2.HelloService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuyu on 2017/5/26.
 */
@RestController
@RequestMapping("/")
public class HelloController {
    @Autowired
    private HelloService helloService;
    @Autowired
    private HelloService2 helloService2;

    @RequestMapping("hello")
    public String hello(@RequestParam String name) {
        return helloService.hello(name) + helloService.toString();
    }

    @RequestMapping("hello2")
    public String hello2(@RequestParam String name) {
        return helloService2.hello(name) + helloService2.toString();
    }
}

