package com.project.config.PropertiesConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = {"classpath:custConfig.properties"})
public class CustConfig {

    @Value("${person.name}")
    private String name;
    @Value("${person.age}")
    private String age;

    @Override
    public String toString() {
        return "CustConfig{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
