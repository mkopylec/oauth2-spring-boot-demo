package com.github.mkopylec.oauth2.authorizationserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class AuthorizationServer {

    public static void main(String[] args) {
        run(AuthorizationServer.class, args);
    }
}
