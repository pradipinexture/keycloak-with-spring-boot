package com.keycloak.adminsite.controller;

import com.keycloak.adminsite.model.CustomKeycloakDeployment;
import com.keycloak.adminsite.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class NewController {
    @Autowired
    KeycloakService keycloakService;
    @GetMapping("/")
    public String getHomePage() throws IOException {
        System.out.println("Home");
        return "redirect:/tenant/"+KeycloakService.currentRealmName+"/users";
    }
    @GetMapping("/tenant/branch1/getdep")
    @ResponseBody
    public List<CustomKeycloakDeployment> getHome() throws IOException {
        String s=null;
        System.out.println(s.equals("sedf"));
        return keycloakService.getAllKeycloakDeployment();
    }

    @GetMapping("/tenant/branch1/getdep/{realm}")
    public CustomKeycloakDeployment getHomedep(@PathVariable String realm) throws IOException {
        return keycloakService.getKeycloakDeploymentByRealm(realm);
    }
}
