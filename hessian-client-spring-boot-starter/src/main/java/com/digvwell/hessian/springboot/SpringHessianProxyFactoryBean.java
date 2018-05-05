package com.digvwell.hessian.springboot;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

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
