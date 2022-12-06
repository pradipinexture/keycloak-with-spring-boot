package com.keycloak.usersite.controller;

import com.keycloak.usersite.service.KeycloakService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
public class UIController {

    @ModelAttribute("siteName")
    public String setSiteName(){
        return "www."+KeycloakService.currentRealmName+".com";
    }


    @GetMapping("/")
    public String getHomePage() throws IOException {
        System.out.println("Home");
        return "redirect:/tenant/"+ KeycloakService.currentRealmName+"/home";
    }
    @RequestMapping("/tenant/{realm}/home")
    public String gePublicPage(Model model){
        return "home";
    }

    @RequestMapping("/tenant/{realm}/profile")
    public String getHomePage(Model model, KeycloakAuthenticationToken authentication){
        setAuthenticationInModel(model,authentication);
        return "user";
    }

    private void setAuthenticationInModel(Model model, KeycloakAuthenticationToken authentication){
        Principal principal = (Principal) authentication.getPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            KeycloakSecurityContext keycloakSecurityContext = kPrincipal.getKeycloakSecurityContext();
            model.addAttribute("kContext",keycloakSecurityContext);
        }
    }
}
