package com.onfresh.cloud.api.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.Stream;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static String getMessage(String key) {
        return applicationContext.getMessage(key, null, Locale.getDefault());
    }

    public static String[] getActiveProfile() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    public static String getActiveProfile(String name) {
        return Stream.of(getActiveProfile()).filter(x -> x.equals(name)).findFirst().orElse(null);
    }
}
