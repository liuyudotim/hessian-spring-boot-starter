package com.digvwell.hessian.springboot;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.caucho.HessianClientInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class SpringHessianClientInterceptor extends HessianClientInterceptor {
    @Autowired
    private LoadBalancerClient loadBalancer;

    private HessianProxyFactory proxyFactory = new HessianProxyFactory();
    private Map hessianProxyMap = new HashMap();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String url = null;

        Object hessianProxy;

        try {
            url = this.getServiceUrl();

            String[] urlItems = url.split("/");
            String service = urlItems[2];
            ServiceInstance serviceInstance = loadBalancer.choose(service);

            if (serviceInstance == null) {
                throw new Exception(service + " not existe, please check the service name or the service instance list.");
            }

            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();
            String serviceUrl = url.replace(service, host + ":" + port);

            hessianProxy = this.hessianProxyMap.get(serviceUrl);
            if (hessianProxy == null) {
                hessianProxy = this.proxyFactory.create(this.getServiceInterface(), serviceUrl, this.getBeanClassLoader());
                this.hessianProxyMap.put(serviceUrl, hessianProxy);
            }
        } catch (MalformedURLException var13) {
            throw new RemoteLookupFailureException("Service URL [" + url + "] is invalid", var13);
        }

        if (hessianProxy == null) {
            throw new IllegalStateException("SpringHessianClientInterceptor is not properly initialized - invoke 'prepare' " +
                    "before attempting any operations");
        } else {

            ClassLoader originalClassLoader = this.overrideThreadContextClassLoader();

            Object var19;
            try {
                Object var18 = invocation.getMethod().invoke(hessianProxy, invocation.getArguments());
                return var18;
            } catch (InvocationTargetException var14) {
                var19 = this.processInvocationException(var14);
            } catch (Exception var15) {
                throw new RemoteProxyFailureException("Failed to invoke Hessian proxy for remote service [" + this
                        .getServiceUrl() + "]", var15);
            } finally {
                this.resetThreadContextClassLoader(originalClassLoader);
            }

            return var19;
        }
    }

    private Object processInvocationException(InvocationTargetException ex) throws Throwable {
        Throwable targetEx = ex.getTargetException();
        if (targetEx instanceof InvocationTargetException) {
            targetEx = ((InvocationTargetException) targetEx).getTargetException();
        }

        if (targetEx instanceof HessianConnectionException) {
            throw this.convertHessianAccessException(targetEx);
        } else if (!(targetEx instanceof HessianException) && !(targetEx instanceof HessianRuntimeException)) {
            if (targetEx instanceof UndeclaredThrowableException) {
                UndeclaredThrowableException utex = (UndeclaredThrowableException) targetEx;
                throw this.convertHessianAccessException(utex.getUndeclaredThrowable());
            } else {
                throw targetEx;
            }
        } else {
            Throwable cause = targetEx.getCause();
            throw this.convertHessianAccessException(cause != null ? cause : targetEx);
        }
    }
}
