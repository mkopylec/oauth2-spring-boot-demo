package com.github.mkopylec.oauth2.client.authorizationcode;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

import static org.springframework.boot.SpringApplication.run;

@EnableOAuth2Sso
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }
}

// TODO Po wymianie kodu na token OAuth2ClientAuthenticationProcessingFilter przekierowuje na / zamiast na /client/authorization-code