package im.liuyu.hessian.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by liuyu on 2017/5/24.
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(String name) {
        logger.info("Hello," + name + "!");
        return "Hello," + name + "!";
    }
}
