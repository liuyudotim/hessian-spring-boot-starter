package im.liuyu.hessian.springboot;

/**
 * Created by liuyu on 2017/7/6.
 */
public class HessianServiceClientScanBean {
    private String basePackage;
    private String serviceName;
    private String hostAndPort;
    private String path;

    public HessianServiceClientScanBean(String basePackage, String serviceName, String path) {
        this.basePackage = basePackage;
        this.serviceName = serviceName;
        this.path = path;
    }

    public HessianServiceClientScanBean(String basePackage, String serviceName, String path, String hostAndPort) {
        this(basePackage, serviceName, path);
        this.hostAndPort = hostAndPort;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPath() {
        return path;
    }

    public String getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(String hostAndPort) {
        this.hostAndPort = hostAndPort;
    }
}
