
package com.keycloak.realmmicroservice.controller;

import com.keycloak.realmmicroservice.service.KeycloakService;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class UIController {

    @Autowired
    KeycloakService keycloakService;

    @GetMapping
    public String getAllRealms(Model model){
        model.addAttribute("realms", keycloakService.getAllRealm());
        return "realms";
    }

    @PostMapping("/addRealm")
    public String addNewRealm(@ModelAttribute  RealmRepresentation realmRepresentation, Model model){
        String realm = realmRepresentation.getRealm();
        String createMessage = keycloakService.addRealm(realmRepresentation);  // First add realm in keycloak
        if(createMessage == "Realm Created"){
            String DBDepMessage=keycloakService.AddKeycloakDepDB(realm);  // Second add realm KeyclaokDeployment in Database for multi-tenant
            String s = keycloakService.addClient(realm);  // Third add clients in newly created realm
            String userMessagekeycloakService=keycloakService.addDefaultUser(realm);  // Fourth add default user in newly created realm
            System.out.println(""+DBDepMessage+""+s+""+userMessagekeycloakService);
        }

        model.addAttribute("createMessage",createMessage);
        model.addAttribute("addedRealm",realm);
        System.out.println(createMessage+"\n");
//        return new RedirectView("http://localhost:2222/tenant/"+realm+"/users");
        return "realm_create";
    }

    @GetMapping("/deleterealm")
    public String recoverPass(@RequestParam("realm") String realm, Model model) {
        String deleteMessage = "Realm not deleted";
        String deleteMessageDB="Realm not deleted";
        if(!realm.equals("master") && !realm.equals("Custom")){
            deleteMessage = keycloakService.deleteRealm(realm);
            deleteMessageDB=keycloakService.deleteKeycloakDepDB(realm);
        }

        model.addAttribute("deleteMessage",deleteMessage);
        System.out.println("\n"+deleteMessage+"\n"+deleteMessageDB+"\n");
        return "realm_delete";
    }

    @GetMapping("/realm_create")
    public String getRealmCreationPage(){
        return "realm_create";
    }

    @GetMapping("/realm_delete")
    public String getRealmdeletionPage(){
        return "realm_delete";
    }

    @GetMapping("/realm_delete/{realm}")
    @ResponseBody
    public String getRealmdeletionPage(@PathVariable String realm){
//        String rolesInRealm = keycloakService.addRoleToUser();
//        System.out.println(rolesInRealm);
        return "rolesInRealm";
    }
}
