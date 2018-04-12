package com.github.mkopylec.oauth2.resourceserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedEndpoint {

    @RequestMapping("/protected")
    public abstract name() {
        return null;
    }
}
