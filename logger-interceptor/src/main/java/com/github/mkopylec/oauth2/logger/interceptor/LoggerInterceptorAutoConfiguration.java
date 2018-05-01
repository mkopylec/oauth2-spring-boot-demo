package com.github.mkopylec.oauth2.logger.interceptor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@EnableConfigurationProperties(LoggerInterceptorProperties.class)
public class LoggerInterceptorAutoConfiguration {

    @Bean
    public FilterRegistrationBean loggerFilter(LoggerInterceptorProperties properties) {
        LoggingFilter filter = new LoggingFilter(properties);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setOrder(HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
