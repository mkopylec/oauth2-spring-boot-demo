package com.github.mkopylec.oauth2.authorizationserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static java.util.Collections.singletonList;
import static org.springframework.security.core.userdetails.User.withUsername;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    public AuthorizationServerConfiguration(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-application")
                .secret("secret")
                .authorizedGrantTypes("authorization_code", "client_credentials", "implicit", "refresh_token", "password")
                .scopes("read-resource")
                .redirectUris("http://127.0.0.1:8085/client/authorization-code")
                .accessTokenValiditySeconds(30)
                .and()
                .withClient("resource-server")
                .secret("secret");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(new InMemoryUserDetailsManager(singletonList(
                        withUsername("resource-owner").password("password").authorities("owner").build()
                )));
    }
}
