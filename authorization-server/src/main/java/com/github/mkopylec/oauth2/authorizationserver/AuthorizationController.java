package com.github.mkopylec.oauth2.authorizationserver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/oauth/confirm_access")
    public String showAccessConfirmationPage(AuthorizationRequest authorizationRequest, HttpServletRequest request) {
        System.out.println(authorizationRequest.getRequestParameters());
        System.out.println(authorizationRequest.getClientId());
        System.out.println(authorizationRequest.getScope());
        return "access-confirmation";
    }
}
