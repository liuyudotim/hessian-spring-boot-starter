package com.digvwell.springboot.hessian;

public class HessianServiceClientScanBean {
    private String basePackage;
    private String serviceName;
    private String path;

    public HessianServiceClientScanBean(String basePackage, String serviceName, String path) {
        this.basePackage = basePackage;
        this.serviceName = serviceName;
        this.path = path;
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
}
