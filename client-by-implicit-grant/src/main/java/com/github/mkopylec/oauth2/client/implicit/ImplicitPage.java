package com.github.mkopylec.oauth2.client.implicit;

import org.springframework.web.servlet.ModelAndView;

public class ImplicitPage extends ModelAndView {

    public ImplicitPage() {
        super("implicit");
    }

    public ImplicitPage setClientId(String clientId) {
        set("clientId", clientId);
        return this;
    }

    public ImplicitPage setResource(String resource) {
        set("resource", resource);
        return this;
    }

    public ImplicitPage setError(String error) {
        set("error", error);
        return this;
    }

    private void set(String attribute, Object value) {
        getModel().put(attribute, value);
    }
}
