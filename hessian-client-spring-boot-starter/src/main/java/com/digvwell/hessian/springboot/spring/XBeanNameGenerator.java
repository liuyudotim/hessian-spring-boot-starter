package com.digvwell.hessian.springboot.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.HashMap;
import java.util.Map;

public class XBeanNameGenerator extends AnnotationBeanNameGenerator {
    private Map<String, Integer> beanNames = new HashMap<>();

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String originalBeanName = super.generateBeanName(definition, registry);
        String beanName = originalBeanName;
        Integer count = beanNames.get(beanName);
        if (count == null) {
            count = 1;
        } else {
            count++;
            beanName = originalBeanName + count;
        }

        beanNames.put(originalBeanName, count);
        return beanName;
    }
}
