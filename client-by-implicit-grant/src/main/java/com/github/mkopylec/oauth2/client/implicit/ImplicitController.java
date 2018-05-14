package com.github.mkopylec.oauth2.client.implicit;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/client/implicit")
public class ImplicitController {

    private final OAuth2ClientProperties clientProperties;

    public ImplicitController(OAuth2ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    @GetMapping
    public ModelAndView showImplicitPage() {
        return new ImplicitPage()
                .setClientId(clientProperties.getClientId());
    }
}
