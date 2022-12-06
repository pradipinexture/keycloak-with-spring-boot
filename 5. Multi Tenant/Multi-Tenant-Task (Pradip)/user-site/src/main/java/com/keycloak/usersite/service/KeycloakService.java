package com.keycloak.usersite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keycloak.usersite.model.CustomKeycloakDeployment;
import com.keycloak.usersite.repository.CustomRepisitory;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class KeycloakService {
    @Autowired
    CustomRepisitory customRepisitory;
    public static String currentRealmName="Custom";

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

}
