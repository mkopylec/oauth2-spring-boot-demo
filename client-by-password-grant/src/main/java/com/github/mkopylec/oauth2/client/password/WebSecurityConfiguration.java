//package com.github.mkopylec.oauth2.client.password;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import static com.github.mkopylec.oauth2.client.password.PasswordController.LOGIN_PAGE_URI;
//import static com.github.mkopylec.oauth2.client.password.PasswordController.PASSWORD_PAGE_URI;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers(PASSWORD_PAGE_URI).authenticated();
//    }
//}
