package com.keycloak.realmmicroservice.repository;


import com.keycloak.realmmicroservice.model.CustomKeycloakDeployment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomRepisitory extends JpaRepository<CustomKeycloakDeployment,Integer> {
    CustomKeycloakDeployment findByRealm(String realm);

    void deleteByRealm(String realm);
}
