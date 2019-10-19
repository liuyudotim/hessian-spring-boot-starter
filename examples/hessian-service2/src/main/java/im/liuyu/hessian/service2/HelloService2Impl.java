package im.liuyu.hessian.service2;


import org.springframework.stereotype.Service;

/**
 * Created by liuyu on 2017/5/24.
 */
@Service
public class HelloService2Impl implements HelloService2 {
    @Override
    public String hello(String name) {
        return "Hello Service2," + name + "!";
    }
}
