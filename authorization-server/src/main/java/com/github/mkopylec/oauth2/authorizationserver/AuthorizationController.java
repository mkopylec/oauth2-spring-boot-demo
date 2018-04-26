package com.github.mkopylec.oauth2.authorizationserver;

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
    public String showAccessConfirmationPage() {
        return "access-confirmation";
    }
}
