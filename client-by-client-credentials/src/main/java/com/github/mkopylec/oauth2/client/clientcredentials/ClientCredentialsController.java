package com.github.mkopylec.oauth2.client.clientcredentials;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
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

    @PostMapping
    public ModelAndView getResource(@RequestParam("type") String type) {
        ClientCredentialsPage page = new ClientCredentialsPage()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret());
        try {
            switch (type) {
                case "public":
                    page.setResource(requestPublicResource());
                    break;
                case "protected":
                    page.setResource(requestProtectedResource());
                    break;
                case "protected-error":
                    page.setResource(requestProtectedResourceError());
                    break;
                case "protected-scope":
                    page.setResource(requestScopeProtectedResource());
                    break;
                case "protected-invalid-scope":
                    page.setResource(requestInvalidScopeProtectedResource());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid resource type requested: " + type);
            }
        } catch (HttpStatusCodeException e) {
            page.setError(e.getResponseBodyAsString());
        } catch (Exception e) {
            page.setError(e.getMessage());
        }
        return page;
    }

    private String requestPublicResource() {
        return restTemplate.getForEntity("http://localhost:8081/resource/public", String.class).getBody();
    }

    private String requestProtectedResource() {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected", String.class).getBody();
    }

    private String requestProtectedResourceError() {
        return restTemplate.getForEntity("http://localhost:8081/resource/protected", String.class).getBody();
    }

    private String requestScopeProtectedResource() {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/scope", String.class).getBody();
    }

    private String requestInvalidScopeProtectedResource() {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/scope/invalid", String.class).getBody();
    }
}
