package im.liuyu.hessian.spring;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.caucho.HessianClientInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyu on 2017/7/6.
 */
public class SpringHessianClientInterceptor extends HessianClientInterceptor {
    private String realServiceUrl;

    @Autowired
    private LoadBalancerClient loadBalancer;

    private HessianProxyFactory proxyFactory = new HessianProxyFactory();
    private static Map hessianProxyMap = new HashMap();

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

            this.realServiceUrl = serviceUrl;

        } catch (MalformedURLException var13) {
            throw new RemoteLookupFailureException("Service URL [" + url + "] is invalid", var13);
        }

        if (hessianProxy == null) {
            throw new IllegalStateException("SpringHessianClientInterceptor is not properly initialized - invoke 'prepare' " +
                    "before attempting any operations");
        } else {

            ClassLoader originalClassLoader = this.overrideThreadContextClassLoader();

            try {
                return invocation.getMethod().invoke(hessianProxy, invocation.getArguments());
            } catch (InvocationTargetException ex) {
                Throwable targetEx = ex.getTargetException();
                // Hessian 4.0 check: another layer of InvocationTargetException.
                if (targetEx instanceof InvocationTargetException) {
                    targetEx = ((InvocationTargetException) targetEx).getTargetException();
                }
                if (targetEx instanceof HessianConnectionException) {
                    throw convertHessianAccessException(targetEx);
                } else if (targetEx instanceof HessianException || targetEx instanceof HessianRuntimeException) {
                    Throwable cause = targetEx.getCause();
                    throw convertHessianAccessException(cause != null ? cause : targetEx);
                } else if (targetEx instanceof UndeclaredThrowableException) {
                    UndeclaredThrowableException utex = (UndeclaredThrowableException) targetEx;
                    throw convertHessianAccessException(utex.getUndeclaredThrowable());
                } else {
                    throw targetEx;
                }
            } catch (Throwable ex) {
                throw new RemoteProxyFailureException("Failed to invoke Hessian proxy for remote service [" + this
                        .getServiceUrl() + "]", ex);
            } finally {
                this.resetThreadContextClassLoader(originalClassLoader);
            }
        }
    }

    @Override
    protected RemoteAccessException convertHessianAccessException(Throwable ex) {
        if (ex instanceof HessianConnectionException || ex instanceof ConnectException) {
            return new RemoteConnectFailureException(
                    "Cannot connect to Hessian remote service at [" + getRealServiceUrl() + "]", ex);
        } else {
            return new RemoteAccessException(
                    "Cannot access Hessian remote service at [" + getRealServiceUrl() + "]", ex);
        }
    }

    public String getRealServiceUrl() {
        return realServiceUrl;
    }
}
