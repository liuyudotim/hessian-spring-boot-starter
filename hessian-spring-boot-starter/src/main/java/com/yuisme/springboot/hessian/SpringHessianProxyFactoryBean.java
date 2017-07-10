package com.yuisme.springboot.hessian;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by liuyu on 2017/7/6.
 */
public class SpringHessianProxyFactoryBean extends SpringHessianClientInterceptor implements FactoryBean<Object> {
    private Object serviceProxy;
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = (new ProxyFactory(this.getServiceInterface(), this)).getProxy(this.getBeanClassLoader());
    }
    @Override
    public Object getObject() {
        return this.serviceProxy;
    }
    @Override
    public Class<?> getObjectType() {
        return this.getServiceInterface();
    }
    @Override
    public boolean isSingleton() {
        return true;
    }
}
