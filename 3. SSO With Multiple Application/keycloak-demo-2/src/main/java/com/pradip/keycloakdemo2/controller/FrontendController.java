package com.pradip.keycloakdemo2.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class FrontendController {
    @RequestMapping
    public String gePublicPage(Model model){
        model.addAttribute("pageName","Home");
        return "home";
    }

    @RequestMapping("/user")
    public String getHomePage(Model model, KeycloakAuthenticationToken authentication){
        setAuthenticationInModel(model,authentication,"User");
        return "user";
    }

    @RequestMapping("/admin")
    public String getAdminPage(Model model, KeycloakAuthenticationToken authentication){
        setAuthenticationInModel(model,authentication,"Admin");
        return "user";
    }

    @RequestMapping("/superadmin")
    public String getSuperAdminPage(Model model, KeycloakAuthenticationToken authentication){
        setAuthenticationInModel(model,authentication,"Super Admin");
        return "user";
    }

    @RequestMapping("/accessdenied")
    public String getAccessDenied(Model model){
        model.addAttribute("pageName","Error");
        return "accessdenied";
    }

    private void setAuthenticationInModel(Model model, KeycloakAuthenticationToken authentication, String pageName) {
        Principal principal = (Principal) authentication.getPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            KeycloakSecurityContext keycloakSecurityContext = kPrincipal.getKeycloakSecurityContext();
            model.addAttribute("kContext", keycloakSecurityContext);
            model.addAttribute("pageName", pageName);
        }
    }
}
