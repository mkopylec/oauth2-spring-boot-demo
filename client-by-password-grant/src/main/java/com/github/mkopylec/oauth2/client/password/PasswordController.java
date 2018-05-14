package com.github.mkopylec.oauth2.client.password;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client/password")
public class PasswordController {

    private final OAuth2ClientProperties clientProperties;
    private final OAuth2ClientContext context;
    private final RestTemplate restTemplate = new RestTemplate();

    public PasswordController(OAuth2ClientProperties clientProperties, OAuth2ClientContext context) {
        this.clientProperties = clientProperties;
        this.context = context;
    }

    @GetMapping
    public ModelAndView showPasswordPage() {
        return new PasswordPage()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret());
    }

    @PostMapping
    public ModelAndView getResource(@RequestParam("type") String type, @RequestParam("username") String username, @RequestParam("password") String password) {
        PasswordPage page = new PasswordPage()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret());
        OAuth2RestTemplate oAuth2RestTemplate = createOAuth2RestTemplate(username, password);
        try {
            switch (type) {
                case "public":
                    page.setResource(requestPublicResource());
                    break;
                case "protected":
                    page.setResource(requestProtectedResource(oAuth2RestTemplate));
                    break;
                case "protected-error":
                    page.setResource(requestProtectedResourceError());
                    break;
                case "protected-scope":
                    page.setResource(requestScopeProtectedResource(oAuth2RestTemplate));
                    break;
                case "protected-invalid-scope":
                    page.setResource(requestInvalidScopeProtectedResource(oAuth2RestTemplate));
                    break;
                case "protected-authority":
                    page.setResource(requestAuthorityScopeProtectedResource(oAuth2RestTemplate));
                    break;
                case "protected-invalid-authority":
                    page.setResource(requestInvalidAuthorityProtectedResource(oAuth2RestTemplate));
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

    private String requestProtectedResource(OAuth2RestTemplate oAuth2RestTemplate) {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected", String.class).getBody();
    }

    private String requestProtectedResourceError() {
        return restTemplate.getForEntity("http://localhost:8081/resource/protected", String.class).getBody();
    }

    private String requestScopeProtectedResource(OAuth2RestTemplate oAuth2RestTemplate) {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/scope", String.class).getBody();
    }

    private String requestInvalidScopeProtectedResource(OAuth2RestTemplate oAuth2RestTemplate) {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/scope/invalid", String.class).getBody();
    }

    private String requestAuthorityScopeProtectedResource(OAuth2RestTemplate oAuth2RestTemplate) {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/authority", String.class).getBody();
    }

    private String requestInvalidAuthorityProtectedResource(OAuth2RestTemplate oAuth2RestTemplate) {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/authority/invalid", String.class).getBody();
    }

    private OAuth2RestTemplate createOAuth2RestTemplate(String username, String password) {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientId(clientProperties.getClientId());
        resource.setClientSecret(clientProperties.getClientSecret());
        resource.setUsername(username);
        resource.setPassword(password);
        resource.setAccessTokenUri("http://localhost:8080/oauth/token");
        return new OAuth2RestTemplate(resource, context);
    }
}
