package com.keycloak.adminsite.repository;


import com.keycloak.adminsite.model.CustomKeycloakDeployment;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface CustomRepisitory extends JpaRepository<CustomKeycloakDeployment,Integer> {
    CustomKeycloakDeployment findByRealm(String realm);
}
