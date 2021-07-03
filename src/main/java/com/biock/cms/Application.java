package com.biock.cms;

import com.biock.cms.config.CmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CmsConfig.class)
public class Application {

    public static void main(final String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
