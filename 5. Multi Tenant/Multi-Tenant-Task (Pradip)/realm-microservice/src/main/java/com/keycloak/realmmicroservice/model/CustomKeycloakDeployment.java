package com.keycloak.realmmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "Keycloak_realm_deployment")
@Component
public class CustomKeycloakDeployment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;
    private String realm;
    @JsonProperty("auth-server-url")
    private String authServerUrl;
    @JsonProperty("ssl-required")
    private String sslRequired;

    private String resource;

    @JsonProperty("public-client")
    private boolean publicClient;

//    private Map<String,String> credientials;

    public CustomKeycloakDeployment() {

    }
    public CustomKeycloakDeployment(String realm, String authServerUrl, String sslRequired, String resource, boolean publicClient) {
        this.realm = realm;
        this.authServerUrl = authServerUrl;
        this.sslRequired = sslRequired;
        this.resource = resource;
        this.publicClient = publicClient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }

    public void setSslRequired(String sslRequired) {
        this.sslRequired = sslRequired;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setPublicClient(boolean publicClient) {
        this.publicClient = publicClient;
    }

    public int getId() {
        return id;
    }

    public String getRealm() {
        return realm;
    }

    public String getAuthServerUrl() {
        return authServerUrl;
    }

    public String getSslRequired() {
        return sslRequired;
    }

    public String getResource() {
        return resource;
    }

    public boolean getPublicClient() {
        return publicClient;
    }
}
