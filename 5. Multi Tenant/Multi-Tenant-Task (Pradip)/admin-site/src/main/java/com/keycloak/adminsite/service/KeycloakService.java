package com.keycloak.adminsite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keycloak.adminsite.repository.CustomRepisitory;
import com.keycloak.adminsite.model.CustomKeycloakDeployment;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class KeycloakService {
    @Autowired
    Keycloak keycloak;

    @Autowired
    CustomRepisitory customRepisitory;

    public static String currentRealmName="Custom";
    public String addRealm(RealmRepresentation realmRepresentation){
        try{
            keycloak.realms().create(realmRepresentation);
        }
        catch (Exception e){
            return "Realm not Created"+e;
        }
        return "Realm Created";
    }

    public List<RealmRepresentation> getAllRealm(){
        return keycloak.realms().findAll();
    }

    public String deleteRealm(String realm){
        try{
            keycloak.realms().realm(realm).remove();
        }
        catch (NotFoundException e){
            return "Realm not found";
        }
        return "Realm deleted";
    }

    public String addUser(UserRepresentation userRepresentation) {

        // Static Data
        userRepresentation.setEnabled(true);
        userRepresentation.getCredentials().get(0).setTemporary(false);
        userRepresentation.getCredentials().get(0).setType(CredentialRepresentation.PASSWORD);

        try {
            Response response = getKeycloakRealmInstance().users().create(userRepresentation);
            if(response.getStatus() != 201)
                throw new Exception("Error from keycloak and status code"+response.getStatus());
        }
        catch (Exception e){
            return "User not created\n"+e;
        }
        return "User Created";
    }

    public String deleteUser(String id) {
        try{
            getKeycloakRealmInstance().users().delete(id);
        }
        catch (NotFoundException e){
            return "User not found";
        }
        catch (Exception e){
            return "User not deleted";
        }
        return "User Deleted";
    }

    public List<UserRepresentation> getAllUser() {
        RealmResource realmResource =getKeycloakRealmInstance();
        return realmResource.users().list();
    }

    public UserRepresentation getUser(String id) {
        RealmResource realmResource =getKeycloakRealmInstance();
        return realmResource.users().get(id).toRepresentation();
    }

    public CustomKeycloakDeployment saveKeycloakDeployment(CustomKeycloakDeployment customKeycloakDeployment){
        return customRepisitory.save(customKeycloakDeployment);
    }

    public CustomKeycloakDeployment saveKeycloakDeployment(String realm){
        return customRepisitory.findByRealm(realm);
    }

    public CustomKeycloakDeployment getKeycloakDeploymentByRealm(String realm) throws JsonProcessingException {
        return customRepisitory.findByRealm(realm);
    }

    public KeycloakDeployment getFinalKeycloakDep(String realm) throws JsonProcessingException {
        CustomKeycloakDeployment keycloakDeploymentByRealm = getKeycloakDeploymentByRealm(realm);
        if(keycloakDeploymentByRealm != null){
            return KeycloakDeploymentBuilder.build(new ByteArrayInputStream(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(keycloakDeploymentByRealm).getBytes()));
        }
        return null;
    }

    public List<CustomKeycloakDeployment> getAllKeycloakDeployment(){
        return customRepisitory.findAll();
    }

    private RealmResource getKeycloakRealmInstance(){
        return keycloak.realm(currentRealmName);
    }
}
