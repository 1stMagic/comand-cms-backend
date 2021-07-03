package com.biock.cms;

import com.biock.cms.config.CmsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    private final CmsConfig config;

    public CorsConfiguration(final CmsConfig config) {

        this.config = config;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {

                registry
                        .addMapping(CmsApi.V1 + "/**")
                        .allowedOrigins(config.getApiAllowedClientOrigins().toArray(String[]::new))
                        .allowedOriginPatterns(config.getApiAllowedClientOriginPatterns().toArray(String[]::new));
            }
        };
    }
}
