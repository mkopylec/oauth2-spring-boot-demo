package com.github.mkopylec.oauth2.resourceserver;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    public static final String PUBLIC_RESOURCE_URI = "/resource/public";
    public static final String PROTECTED_RESOURCE_URI = "/resource/protected";
    public static final String SCOPE_PROTECTED_RESOURCE_URI = "/resource/protected/scope";
    public static final String INVALID_SCOPE_PROTECTED_RESOURCE_URI = "/resource/protected/scope/invalid";
    public static final String AUTHORITY_PROTECTED_RESOURCE_URI = "/resource/protected/authority";
    public static final String INVALID_AUTHORITY_PROTECTED_RESOURCE_URI = "/resource/protected/authority/invalid";

    @GetMapping(PUBLIC_RESOURCE_URI)
    public String getPublicResource() {
        return "I'm a public resource";
    }

    @GetMapping(PROTECTED_RESOURCE_URI)
    public String getProtectedResource() {
        return "I'm a protected resource";
    }

    @GetMapping(SCOPE_PROTECTED_RESOURCE_URI)
    public String getScopeProtectedResource(OAuth2Authentication authentication) {
        return "I'm a resource protected by " + authentication.getOAuth2Request().getScope() + " scope";
    }

    @GetMapping(INVALID_SCOPE_PROTECTED_RESOURCE_URI)
    public void getInvalidScopeProtectedResource() {
    }

    @GetMapping(AUTHORITY_PROTECTED_RESOURCE_URI)
    public String getAuthorityProtectedResource(OAuth2Authentication authentication) {
        return "I'm a resource protected by " + authentication.getUserAuthentication().getAuthorities() + " authority";
    }

    @GetMapping(INVALID_AUTHORITY_PROTECTED_RESOURCE_URI)
    public void getInvalidAuthorityProtectedResource() {
    }
}
