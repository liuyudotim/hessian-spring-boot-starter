package com.digvwell.hessian.springboot;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.*;

public class HessianServiceClientScannerRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public final static Map<String, HessianServiceClientScanBean> serviceMap = new HashMap<>();

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<AnnotationAttributes> servicesAnnotationAttributes = new ArrayList<>();

        Map<String, Object> scanAnnotationAttributesMap = importingClassMetadata.getAnnotationAttributes(HessianServiceClientScan.class.getName());

        AnnotationAttributes scanAnnoAttrs = AnnotationAttributes
                .fromMap(scanAnnotationAttributesMap);
        if (scanAnnoAttrs != null) {
            servicesAnnotationAttributes.add(scanAnnoAttrs);
        }

        Map<String, Object> scansAnnoAttrsMap = importingClassMetadata.getAnnotationAttributes(HessianServiceClientScans.class.getName());
        AnnotationAttributes scansAnnotationAttributes = AnnotationAttributes
                .fromMap(scansAnnoAttrsMap);
        if (scansAnnotationAttributes != null) {
            servicesAnnotationAttributes.addAll(Arrays.asList(scansAnnotationAttributes.getAnnotationArray("value")));
            registerBeanDefinitions(servicesAnnotationAttributes, registry);

        }
    }

    void registerBeanDefinitions(List<AnnotationAttributes> annoAttrsList, BeanDefinitionRegistry registry) {

        ClassPathHessianClientScanner scanner = new ClassPathHessianClientScanner(registry);

        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        List<String> basePackages = new ArrayList<>();

        for (AnnotationAttributes annoAttrs : annoAttrsList) {
            String basePackage = annoAttrs.getString("basePackage");
            String serviceName = annoAttrs.getString("serviceName");
            String path = annoAttrs.getString("path");

            basePackages.add(basePackage);


            serviceMap.put(basePackage, new HessianServiceClientScanBean(basePackage, serviceName, path));
        }


        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    /**
     * A {@link HessianServiceClientScannerRegister} for {@link HessianServiceClientScan}.
     */
    static class RepeatingRegister extends HessianServiceClientScannerRegister {
        /**
         * {@inheritDoc}
         */
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {
            List<AnnotationAttributes> servicesAnnotationAttributes = new ArrayList<>();

            Map<String, Object> scanAnnotationAttributesMap = importingClassMetadata.getAnnotationAttributes(HessianServiceClientScan.class.getName());

            AnnotationAttributes scanAnnoAttrs = AnnotationAttributes
                    .fromMap(scanAnnotationAttributesMap);
            if (scanAnnoAttrs != null) {
                servicesAnnotationAttributes.add(scanAnnoAttrs);
            }

            Map<String, Object> scansAnnoAttrsMap = importingClassMetadata.getAnnotationAttributes(HessianServiceClientScans.class.getName());
            AnnotationAttributes scansAnnotationAttributes = AnnotationAttributes
                    .fromMap(scansAnnoAttrsMap);
            if (scansAnnotationAttributes != null) {
                servicesAnnotationAttributes.addAll(Arrays.asList(scansAnnotationAttributes.getAnnotationArray("value")));
                registerBeanDefinitions(servicesAnnotationAttributes, registry);

            }
        }
    }
}
