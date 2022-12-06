package com.keycloak.realmmicroservice.service;

import com.keycloak.realmmicroservice.model.CustomKeycloakDeployment;
import com.keycloak.realmmicroservice.repository.CustomRepisitory;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakService {
    @Autowired
    Keycloak keycloak;

    @Autowired
    CustomRepisitory customRepisitory;

    @Autowired
    Environment environment;

    public String addRealm(RealmRepresentation realmRepresentation){
        realmRepresentation.setEnabled(true);
        realmRepresentation.setRegistrationAllowed(true);
        try{
            keycloak.realms().create(realmRepresentation);
            String roleMessage=createRolesInRealm(realmRepresentation.getRealm());
            System.out.println(roleMessage);
        }
        catch (ClientErrorException e){
            return "Realm already exist.";
        }
        catch (Exception e){
            return "Realm not Created.";
        }
        return "Realm Created";
    }

    public String createRolesInRealm(String realm) {
        try{
            RoleRepresentation  roleRepresentation= new RoleRepresentation();
            roleRepresentation.setName("admin");
            roleRepresentation.setDescription("This is user role");
            keycloak.realm(realm).roles().create(roleRepresentation);


            RoleRepresentation  roleRepresentation2= new RoleRepresentation();
            roleRepresentation2.setName("user");
            roleRepresentation2.setDescription("This is user role");
            keycloak.realm(realm).roles().create(roleRepresentation2);

        }
        catch (Exception e){
            return "Roles not created in realm.";
        }
        return "Roles is Created in realm.";
    }

    public String addClient(String realm){
        try{
            ClientRepresentation clientRepresentation=new ClientRepresentation();
            clientRepresentation.setEnabled(true);
            clientRepresentation.setClientId("dynamic-software");
            clientRepresentation.setName("Dynamic Software");
            clientRepresentation.setRedirectUris(Arrays.asList("*"));
            clientRepresentation.setPublicClient(true);
            clientRepresentation.setWebOrigins(Arrays.asList(""));
            clientRepresentation.setProtocol("openid-connect");
            keycloak.realms().realm(realm).clients().create(clientRepresentation);
        }
        catch (ClientErrorException e){
            return "Client already exist.";
        }
        catch (Exception e){
            return "Client not Created.\n"+e;
        }
        return "Client Created";
    }

    public List<RealmRepresentation> getAllRealm(){
        return keycloak.realms().findAll()
                .stream()
                .filter(x -> !x.getRealm().equals("master") && !x.getRealm().equals("Custom"))
                .collect(Collectors.toList());
    }

    public String deleteRealm(String realm){
        try{
            keycloak.realms().realm(realm).remove();
        }
        catch (NotFoundException e){
            return "Realm not found";
        }
        return "Realm Deleted";
    }

    public String addUser(UserRepresentation userRepresentation,String realmName) {
        try{
            UsersResource usersRessourceNew = keycloak.realm(realmName).users();
            usersRessourceNew.create(userRepresentation);
            String userId = usersRessourceNew.search("master").get(0).getId();
            addRoleToUser(realmName,userId);
        }
        catch (Exception e){
            return "User not added";
        }
        return "User Added";
    }

    public String deleteUser(String id) {
        getKeycloakRealmInstance().users().delete(id);
        return "User deleted";
    }

    public List<UserRepresentation> getAllUser() {
        RealmResource realmResource =getKeycloakRealmInstance();
        return realmResource.users().list();
    }

    public UserRepresentation getUser(String id) {
        RealmResource realmResource =getKeycloakRealmInstance();
        return realmResource.users().get(id).toRepresentation();
    }

    private RealmResource getKeycloakRealmInstance(){
        return keycloak.realm("Custom");
    }

    public String addDefaultUser(String realm) {
        UserRepresentation userRepresentation=new UserRepresentation();
        userRepresentation.setUsername("master");
        userRepresentation.setFirstName("Default");
        userRepresentation.setLastName("Admin");
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue("123");
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);

        userRepresentation.setCredentials(Arrays.asList(credentialRepresentation));

        return addUser(userRepresentation,realm);
    }

    public String addRoleToUser(String realm, String userId) {
        try{
            List<RoleRepresentation> realmRolesList = keycloak.realm(realm).roles().list();
            keycloak.realm(realm).users().get(userId).roles().realmLevel().add(realmRolesList);
        }
        catch (Exception e){
            return "Roles are not assigned to user.";
        }
        return "Roles are assigned to user.";
    }

    public  String AddKeycloakDepDB(String realm){
        CustomKeycloakDeployment deployment=new CustomKeycloakDeployment(realm,"http://localhost:8080","external","dynamic-software",true);
        CustomKeycloakDeployment save = customRepisitory.save(deployment);
        if (save == null)
            return  "Keycloak deployment is not Added in DB.";
        return  "Keycloak deployment Added in DB.";
    }
    public  String deleteKeycloakDepDB(String realm){
        customRepisitory.deleteByRealm(realm);
        return  "Keycloak deployment deleted.";
    }
}
