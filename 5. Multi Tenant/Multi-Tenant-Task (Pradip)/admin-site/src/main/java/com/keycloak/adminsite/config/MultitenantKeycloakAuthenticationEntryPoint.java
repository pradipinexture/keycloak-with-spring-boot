package com.keycloak.adminsite.config;

import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class MultitenantKeycloakAuthenticationEntryPoint extends KeycloakAuthenticationEntryPoint {

    public MultitenantKeycloakAuthenticationEntryPoint(AdapterDeploymentContext adapterDeploymentContext) {
        super(adapterDeploymentContext);
    }

    public MultitenantKeycloakAuthenticationEntryPoint(AdapterDeploymentContext adapterDeploymentContext, RequestMatcher apiRequestMatcher) {
        super(adapterDeploymentContext, apiRequestMatcher);
    }

    @Override
    protected void commenceLoginRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getRequestURI();
        int multitenantIndex = path.indexOf("tenant/");
        if (multitenantIndex != -1) {
            String realm = path.substring(path.indexOf("tenant/")).split("/")[1];
            if (realm.contains("?")) {
                realm = realm.split("\\?")[0];
            }

            String contextAwareLoginUri = request.getContextPath() + "/tenant/" + realm + DEFAULT_LOGIN_URI;
            response.sendRedirect(contextAwareLoginUri);
//            throw new IllegalStateException("Not able to resolve the realm from the request path!");
        }


    }
}
