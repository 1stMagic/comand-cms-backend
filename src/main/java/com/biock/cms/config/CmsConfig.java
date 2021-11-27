package com.biock.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Validated
@ConstructorBinding
@ConfigurationProperties("cms")
public class CmsConfig {

    @NotNull
    private final Path repositoryPath;

    @NotNull
    private final Path assetsPath;

    @NotBlank
    private final String timeZone;

    @NotBlank
    private final String dateFormat;

    @NotBlank
    private final String timeFormat;

    @NotBlank
    private final String jwtSecret;

    private final List<String> apiAllowedMethods;

    private final List<String> apiAllowedClientOrigins;

    private final List<String> apiAllowedClientOriginPatterns;

    public CmsConfig(
            final Path repositoryPath,
            final Path assetsPath,
            final String timeZone,
            final String dateFormat,
            final String timeFormat,
            final String jwtSecret,
            final List<String> apiAllowedMethods,
            final List<String> apiAllowedClientOrigins,
            final List<String> apiAllowedClientOriginPatterns) {

        this.repositoryPath = repositoryPath;
        this.assetsPath = assetsPath;
        this.timeZone = timeZone;
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
        this.jwtSecret = jwtSecret;
        this.apiAllowedMethods = Optional.ofNullable(apiAllowedMethods).orElse(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        this.apiAllowedClientOrigins = Optional.ofNullable(apiAllowedClientOrigins).orElse(emptyList());
        this.apiAllowedClientOriginPatterns = Optional.ofNullable(apiAllowedClientOriginPatterns).orElse(emptyList());
    }

    public Path getRepositoryPath() {

        return this.repositoryPath;
    }

    public Path getAssetsPath() {

        return this.assetsPath;
    }

    public String getTimeZone() {

        return this.timeZone;
    }

    public ZoneId getTimeZoneId() {

        return ZoneId.of(getTimeZone());
    }

    public ZoneOffset getTimeZoneOffset() {

        return getTimeZoneId().getRules().getOffset(Instant.now());
    }

    public String getDateFormat() {

        return this.dateFormat;
    }

    public String getTimeFormat() {

        return this.timeFormat;
    }

    public String getJwtSecret() {

        return this.jwtSecret;
    }

    public List<String> getApiAllowedMethods() {

        return this.apiAllowedMethods;
    }

    public List<String> getApiAllowedClientOrigins() {

        return this.apiAllowedClientOrigins;
    }

    public List<String> getApiAllowedClientOriginPatterns() {

        return this.apiAllowedClientOriginPatterns;
    }
}
