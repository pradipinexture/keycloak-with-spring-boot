package com.keycloak.adminsite.controller;

import com.keycloak.adminsite.service.KeycloakService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tenant")
public class KeycloakController {


    @Autowired
    KeycloakService keycloakService;

    // Set siteName in all APIS
    @ModelAttribute("siteName")
    public String setSiteName(){
        return "www."+KeycloakService.currentRealmName+".com";
    }

    // UI Pages
    @GetMapping("/{realm}/users")
    public String getAllUsers(Model model){
        model.addAttribute("users",keycloakService.getAllUser());
        return "users";
    }

    @GetMapping("/{realm}/user_create")
    public String getUserCreationPage(){
        return "user_create";
    }

    @GetMapping("/{realm}/user_delete")
    public String getUserdeletionPage(){
        return "user_delete";
    }

    // Backend APIS
    @PostMapping("/{realm}/addUser")
    public String addNewRealm(@ModelAttribute UserRepresentation user, Model model){
        String createMessage = keycloakService.addUser(user);
        model.addAttribute("createMessage",createMessage);
        System.out.println(createMessage);

        return "user_create";
    }

    @GetMapping("/{realm}/deleteuser")
    public String deleteUser(@RequestParam("userId") String userId, Model model) {
        String deleteMessage = keycloakService.deleteUser(userId);
        model.addAttribute("deleteMessage",deleteMessage);
        System.out.println(deleteMessage);
        return "user_delete";
    }
    @GetMapping("/{realm}/accessdenied")
    public String getAcccessDeniedPage(Model model) {
        model.addAttribute("currentRealm",KeycloakService.currentRealmName);
        return "accessdenied";
    }
}
