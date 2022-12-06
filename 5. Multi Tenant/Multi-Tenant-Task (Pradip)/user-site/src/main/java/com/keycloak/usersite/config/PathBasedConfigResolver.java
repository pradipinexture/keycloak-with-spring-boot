package com.keycloak.usersite.config;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.keycloak.usersite.service.KeycloakService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@ConditionalOnProperty(prefix = "keycloak.config", name = "resolver", havingValue = "path")
public class PathBasedConfigResolver implements KeycloakConfigResolver {

    private final ConcurrentHashMap<String, KeycloakDeployment> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    @Autowired
    KeycloakService keycloakService;

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {

        String path = request.getURI();
        if (path.indexOf("error") == -1) {
            if (path.indexOf("tenant/") == -1) {
                return new KeycloakDeployment();
//                throw new IllegalStateException("Not able to resolve realm from the request path!"+ path);
            }
            String realm = path.substring(path.indexOf("tenant/")).split("/")[1]; // get realm path be like /tenant/realmName

            // Check logged in user realm and request realm are same or not
            userRealmCheck(realm);

            // if url contain ? after realName then it remove all contain after ?
            if (realm.contains("?")) {
                realm = realm.split("\\?")[0];
            }
            // description if cache not have any realm deployment then we get it from json file and add it in cache
            try {
                setRealmInCache(realm);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            KeycloakService.currentRealmName=realm; // This is variable used while we sent request to keycloak
            return cache.get(realm);
        }
        return new KeycloakDeployment();
    }

    private void setRealmInCache(String realm) throws JsonProcessingException {
        if (!cache.containsKey(realm)) {
            KeycloakDeployment finalKeycloakDep = keycloakService.getFinalKeycloakDep(realm);
            if(finalKeycloakDep != null){
                cache.put(realm, finalKeycloakDep);
            }
            else
                throw new IllegalStateException(realm+ " realm json file not found in project.");
        }
    }

    private void userRealmCheck(String realm) {
        KeycloakAuthenticationToken auth =(KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            KeycloakSecurityContext keycloakSecurityContext = ((KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal()).getKeycloakSecurityContext();
            String sessionRealm=keycloakSecurityContext.getRealm();

            if(!realm.equals(sessionRealm)){
                throw new IllegalStateException("Please login with "+sessionRealm+" realm.");
            }
        }
    }

    static void setAdapterConfig(AdapterConfig adapterConfig) {
        PathBasedConfigResolver.adapterConfig = adapterConfig;
    }

}