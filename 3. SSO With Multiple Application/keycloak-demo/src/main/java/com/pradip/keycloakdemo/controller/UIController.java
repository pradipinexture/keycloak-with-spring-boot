package com.pradip.keycloakdemo.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Set;

@Controller
public class UIController {
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

    @RequestMapping("/accessdenied")
    public String getAccessDenied(Model model){
        model.addAttribute("pageName","Error");
        return "accessdenied";
    }

    private void setAuthenticationInModel(Model model, KeycloakAuthenticationToken authentication, String pageName){
        //        KeycloakAuthenticationToken authentication =
//                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Principal principal = (Principal) authentication.getPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            KeycloakSecurityContext keycloakSecurityContext = kPrincipal.getKeycloakSecurityContext();
            model.addAttribute("kContext",keycloakSecurityContext);
//            keycloakSecurityContext.getTokenString();
//            keycloakSecurityContext.getIdTokenString();
//            keycloakSecurityContext.getTokenString();
//
//            AccessToken accessToken = keycloakSecurityContext.getToken();
//            Set<String> roles = accessToken.getRealmAccess().getRoles();
//            accessToken.getIat();
//
//
//            IDToken token = keycloakSecurityContext.getIdToken();
//            token.getEmail();
//            token.getEmailVerified();
//            token.getIssuer();
//            token.getIssuedFor();
        }
//        UserDetails userDetails = (UserDetails) a.getPrincipal();
//        System.out.println("User has authorities: " + userDetails.getAuthorities());
//        System.out.println(a.getCredentials());
//
//        System.out.println(a.getName());
//        System.out.println(a.getDetails());
//        System.out.println(a.getPrincipal());
//        System.out.println(a.isAuthenticated());

        model.addAttribute("pageName",pageName);
    }
}
