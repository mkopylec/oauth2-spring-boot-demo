package com.github.mkopylec.oauth2.client.password;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.DefaultUserInfoRestTemplateFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.List;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

@Configuration
public class PasswordConfiguration {

    // TODO session scope jezeli za kazdym razem nie bedziemy podawac usera na webie, jezeli za kazdym razem bedziemy podawac usera na webie to refresh token nigdy sie nie zadzieje
    // TODO trzeba zrobic sesje z userem i opcje "wyloguj", mozna dac SS form login zeby przepuszczalo kazdego usera, bo potem oauthresttemplate bedzie weryfikowal czy user jest poprawny.
    @Bean
    @Scope(value = "session", proxyMode = INTERFACES)
    public OAuth2ClientContext oAuth2ClientContext() {
        return new DefaultOAuth2ClientContext();
    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("security.oauth2.client")
////    @Scope(value = "request", proxyMode = INTERFACES)
//    public ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
//        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
//        details.setUsername("resource-owner");
//        details.setPassword("password");
//        return details;
//    }
//
//    @Bean
//    public UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer() {
//        return template -> template.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
//    }
//
//    @Bean
//    public UserInfoRestTemplateFactory userInfoRestTemplateFactory(
//            ObjectProvider<List<UserInfoRestTemplateCustomizer>> customizers,
//            @Qualifier("resourceOwnerPasswordResourceDetails") ObjectProvider<OAuth2ProtectedResourceDetails> details,
//            ObjectProvider<OAuth2ClientContext> context
//    ) {
//
//        return new DefaultUserInfoRestTemplateFactory(customizers, details, context);
//    }
}
