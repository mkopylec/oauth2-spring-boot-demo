package com.github.mkopylec.oauth2.logger.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("logger-interceptor")
public class LoggerInterceptorProperties {

    private String applicationName;
    private List<String> excludedUriPatterns = new ArrayList<>();

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<String> getExcludedUriPatterns() {
        return excludedUriPatterns;
    }

    public void setExcludedUriPatterns(List<String> excludedUriPatterns) {
        this.excludedUriPatterns = excludedUriPatterns;
    }
}
