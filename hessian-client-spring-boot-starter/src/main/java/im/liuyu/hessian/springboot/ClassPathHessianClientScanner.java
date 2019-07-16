package im.liuyu.hessian.springboot;

import im.liuyu.hessian.spring.SpringHessianProxyFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

/**
 * Created by liuyu on 2017/7/6.
 */
public class ClassPathHessianClientScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathHessianClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (!beanDefinitions.isEmpty()) {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    public void registerFilters() {
        // accepts all classes
        addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });

        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String className = metadataReader.getClassMetadata().getClassName();
                return className.endsWith("package-info");
            }
        });
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();

            if (logger.isDebugEnabled()) {
                logger.debug("Creating SpringHessianProxyFactoryBean with name '" + holder.getBeanName()
                        + "' and '" + definition.getBeanClassName() + "' mapperInterface");
            }

            // the service interface is the original class of the bean
            // but, the actual class of the bean is SpringHessianProxyFactoryBean
            definition.setBeanClass(SpringHessianProxyFactoryBean.class);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

            if (definition instanceof ScannedGenericBeanDefinition) {
                String serviceInterface = ((ScannedGenericBeanDefinition) definition).getMetadata().getClassName();
                String packageName = serviceInterface.substring(0, serviceInterface.lastIndexOf("."));

                HessianServiceClientScanBean scanBean = HessianServiceClientScannerRegistrar.serviceMap.get(packageName);
                String host = scanBean.getServiceName();
                String path = scanBean.getPath() + "/" + serviceInterface.substring(serviceInterface.lastIndexOf('.') + 1);

                definition.getPropertyValues().addPropertyValue("serviceInterface", serviceInterface);
                definition.getPropertyValues().addPropertyValue("serviceUrl", "http://" + host + path);
            }
        }
    }

    @Override
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        return super.findCandidateComponents(basePackage);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}

