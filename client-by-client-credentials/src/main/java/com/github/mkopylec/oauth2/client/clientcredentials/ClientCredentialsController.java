package com.github.mkopylec.oauth2.client.clientcredentials;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client/client-credentials")
public class ClientCredentialsController {

    private final OAuth2ClientProperties clientProperties;
    private final RestTemplate restTemplate;
    private final OAuth2RestTemplate oAuth2RestTemplate;

    public ClientCredentialsController(OAuth2ClientProperties clientProperties, OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        this.clientProperties = clientProperties;
        restTemplate = new RestTemplate();
        oAuth2RestTemplate = new OAuth2RestTemplate(resource, context);
    }

    @GetMapping
    public ModelAndView showClientCredentialsPage() {
        return new ClientCredentialsPage()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret());
    }

    @PostMapping("/resource/public")
    public ModelAndView getPublicResource() {
        return new ClientCredentialsPage()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret())
                .setResource(requestPublicResource());
    }

    private String requestPublicResource() {
        return restTemplate.getForEntity("http://localhost:8081/resource/public", String.class).getBody();
    }

    private String requestProtectedResource() {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected", String.class).getBody();
    }
}
