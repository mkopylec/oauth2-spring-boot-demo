package com.github.mkopylec.oauth2.resourceserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    public static final String PROTECTED_RESOURCE_URI = "/resource/protected";
    public static final String PUBLIC_RESOURCE_URI = "/resource/public";

    @GetMapping(PROTECTED_RESOURCE_URI)
    public String getProtectedResource() {
        return "I'm a protected resource";
    }

    @GetMapping(PUBLIC_RESOURCE_URI)
    public String getPublicResource() {
        return "I'm a public resource";
    }
}
