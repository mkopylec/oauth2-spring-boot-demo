package com.github.mkopylec.oauth2.logger.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("logger-interceptor")
public class LoggerInterceptorProperties {

    private String applicationName;
    // TODO Exclude URLs like /bootstrap.min.css

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
