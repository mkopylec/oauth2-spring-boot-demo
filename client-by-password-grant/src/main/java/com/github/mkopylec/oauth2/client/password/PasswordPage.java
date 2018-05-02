package com.github.mkopylec.oauth2.client.password;

import org.springframework.web.servlet.ModelAndView;

public class PasswordPage extends ModelAndView {

    public PasswordPage() {
        super("password");
    }

    public PasswordPage setClientId(String clientId) {
        set("clientId", clientId);
        return this;
    }

    public PasswordPage setClientSecret(String clientSecret) {
        set("clientSecret", clientSecret);
        return this;
    }

    public PasswordPage setResource(String resource) {
        set("resource", resource);
        return this;
    }

    public PasswordPage setError(String error) {
        set("error", error);
        return this;
    }

    private void set(String attribute, Object value) {
        getModel().put(attribute, value);
    }
}
