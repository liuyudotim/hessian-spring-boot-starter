package im.liuyu.hessian.springboot;

import com.caucho.hessian.client.HessianProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Created by liuyu on 2017/7/6.
 */
@Import(HessianServiceClientScannerRegistrar.class)
public class HessianClientAutoConfiguration {
    @Bean
    public HessianProxyFactory test(){
        HessianProxyFactory factory = new HessianProxyFactory();
        return factory;
    }
}
