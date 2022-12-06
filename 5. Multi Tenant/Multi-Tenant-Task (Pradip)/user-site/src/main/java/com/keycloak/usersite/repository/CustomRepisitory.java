package com.keycloak.usersite.repository;


import com.keycloak.usersite.model.CustomKeycloakDeployment;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface CustomRepisitory extends JpaRepository<CustomKeycloakDeployment,Integer> {
    CustomKeycloakDeployment findByRealm(String realm);
}
