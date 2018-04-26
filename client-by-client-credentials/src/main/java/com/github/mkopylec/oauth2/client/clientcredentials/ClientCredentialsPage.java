package com.github.mkopylec.oauth2.client.clientcredentials;

import org.springframework.web.servlet.ModelAndView;

public class ClientCredentialsPage extends ModelAndView {

    public ClientCredentialsPage() {
        super("client-credentials");
    }

    public ClientCredentialsPage setClientId(String clientId) {
        set("clientId", clientId);
        return this;
    }

    public ClientCredentialsPage setClientSecret(String clientSecret) {
        set("clientSecret", clientSecret);
        return this;
    }

    public ClientCredentialsPage setResource(String resource) {
        set("resource", resource);
        return this;
    }

    public ClientCredentialsPage setError(String error) {
        set("error", error);
        return this;
    }

    private void set(String attribute, Object value) {
        getModel().put(attribute, value);
    }
}
