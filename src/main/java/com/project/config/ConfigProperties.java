package com.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = ConfigProperties.CONFIG_PREFIX)
public class ConfigProperties {

    public static final String CONFIG_PREFIX = "config";

    private String destdir;
    private String imgUrl;
    private String applyUrl;

}
