package com.github.mkopylec.oauth2.client.authorizationcode;

import org.springframework.web.servlet.ModelAndView;

public class AuthorizationCodePage extends ModelAndView {

    public AuthorizationCodePage() {
        super("authorization-code");
    }

    public AuthorizationCodePage setClientId(String clientId) {
        set("clientId", clientId);
        return this;
    }

    public AuthorizationCodePage setClientSecret(String clientSecret) {
        set("clientSecret", clientSecret);
        return this;
    }

    public AuthorizationCodePage setResource(String resource) {
        set("resource", resource);
        return this;
    }

    public AuthorizationCodePage setError(String error) {
        set("error", error);
        return this;
    }

    private void set(String attribute, Object value) {
        getModel().put(attribute, value);
    }
}
