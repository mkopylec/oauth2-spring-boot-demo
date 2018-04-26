package com.github.mkopylec.oauth2.resourceserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static com.github.mkopylec.oauth2.resourceserver.ResourceController.PROTECTED_RESOURCE_URI;
import static com.github.mkopylec.oauth2.resourceserver.ResourceController.PUBLIC_RESOURCE_URI;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PROTECTED_RESOURCE_URI).authenticated()
                .antMatchers(PUBLIC_RESOURCE_URI).permitAll();
    }
}
