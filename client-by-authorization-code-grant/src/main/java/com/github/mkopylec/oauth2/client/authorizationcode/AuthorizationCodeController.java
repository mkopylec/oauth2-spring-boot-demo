package com.github.mkopylec.oauth2.client.authorizationcode;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import static java.net.URI.create;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequestMapping("/client/authorization-code")
public class AuthorizationCodeController {

    private static final Logger log = getLogger(AuthorizationCodeController.class);

    private final OAuth2ClientProperties clientProperties;
    private final RestTemplate restTemplate;
    private final OAuth2RestTemplate oAuth2RestTemplate;

    public AuthorizationCodeController(OAuth2ClientProperties clientProperties, OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        this.clientProperties = clientProperties;
        restTemplate = new RestTemplate();
        this.oAuth2RestTemplate = new OAuth2RestTemplate(resource, context);
    }

    @GetMapping
    public ModelAndView showAuthorizationCodePage() {
        return new AuthorizationCodePage()
                .setClientId(clientProperties.getClientId())
                .setClientSecret(clientProperties.getClientSecret());
    }

    @PostMapping
    public ModelAndView getResource(@RequestParam("type") String type) {
        AuthorizationCodePage page = new AuthorizationCodePage()
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
                case "protected-authority":
                    page.setResource(requestAuthorityProtectedResource());
                    break;
                case "protected-invalid-authority":
                    page.setResource(requestInvalidAuthorityProtectedResource());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid resource type requested: " + type);
            }
        } catch (HttpStatusCodeException e) {
            page.setError(e.getResponseBodyAsString());
        } catch (Exception e) {
            page.setError(e.getMessage());
            log.error("Error requesting resource", e);
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

    private String requestAuthorityProtectedResource() {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/authority", String.class).getBody();
    }

    private String requestInvalidAuthorityProtectedResource() {
        return oAuth2RestTemplate.getForEntity("http://localhost:8081/resource/protected/authority/invalid", String.class).getBody();
    }

    private RequestEntity<Void> createRequest(OAuth2Authentication authentication, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Bearer " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return new RequestEntity<>(headers, HttpMethod.GET, create(url));
    }
}
