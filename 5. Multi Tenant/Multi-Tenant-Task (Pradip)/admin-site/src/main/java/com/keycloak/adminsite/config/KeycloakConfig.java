package com.keycloak.adminsite.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
    private String realm = "master";
    private String clientId = "admin-cli";
    private String clientSecret = "";
    @Value("${keycloak-master-admin-username}")
    private String userName;
    @Value("${keycloak-master-admin-password}")
    private String password;
    private String grantType="password";

    @Bean
     public  Keycloak getInstance(){
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(grantType)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                    .build();
        System.out.println("Keycloak bean created");
        return keycloak;
    }
}
