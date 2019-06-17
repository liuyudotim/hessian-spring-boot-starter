package im.liuyu.hessian.springboot;

import im.liuyu.hessian.annotation.HessianServiceClientScan;
import im.liuyu.hessian.annotation.HessianServiceClientScans;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * Created by liuyu on 2019/6/14.
 */
public class HessianServiceClientScannerRegistrar implements ImportBeanDefinitionRegistrar {

    public final static Map<String, HessianServiceClientScanBean> serviceMap = new HashMap<>();

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

        List<String> basePackages = new ArrayList<>();

        for (AnnotationAttributes annoAttrs : annoAttrsList) {
            String basePackage = annoAttrs.getString("basePackage");
            String serviceName = annoAttrs.getString("serviceName");
            String hostAndPort = annoAttrs.getString("hostAndPort");
            String path = annoAttrs.getString("path");

            basePackages.add(basePackage);

            serviceMap.put(basePackage, new HessianServiceClientScanBean(basePackage, serviceName, path, hostAndPort));
        }

        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
}
