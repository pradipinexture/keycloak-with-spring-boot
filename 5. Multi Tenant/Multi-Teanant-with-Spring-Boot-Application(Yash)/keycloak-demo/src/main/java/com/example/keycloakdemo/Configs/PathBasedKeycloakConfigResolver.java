package com.example.keycloakdemo.Configs;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PathBasedKeycloakConfigResolver implements KeycloakConfigResolver {

    private final ConcurrentHashMap<String, KeycloakDeployment> realmCache = new ConcurrentHashMap<>();

    String githubUsername;

    String githubRepository;

    public PathBasedKeycloakConfigResolver(){
        Properties properties = new Properties();

        InputStream applicationPropertiesStream = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(applicationPropertiesStream);
        }catch(Exception e){
            throw new RuntimeException("Properties files cannot be read.");
        }

        githubUsername = properties.getProperty("github.username");
        githubRepository = properties.getProperty("github.repository");
    }

    private final String GITHUB_URL = "https://raw.githubusercontent.com/";
    private final String SLASH = "/";
    private final String MAIN = "main";
    private final String SUFFIX = "-realm.json";

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        String path = request.getURI();
        String realm = null;
        int multitenantIndex = path.indexOf("tenant/");

        if(multitenantIndex != -1){
            String arr[] = path.substring(path.indexOf("tenant/")).split("/");
            if(arr.length>1) {
                realm = arr[1];
            }
        }
        if(realm == null){
            realm = "default";
        }
        synchronized(realmCache){
            if (realmCache.containsKey(realm)) {
                return realmCache.get(realm);
            }
            String json = getRealm(realm);
            if(json.equals("")){
                return null;
            }
            InputStream is = IOUtils.toInputStream(json);
            realmCache.put(realm, KeycloakDeploymentBuilder.build(is));
            return realmCache.getOrDefault(realm, null);
        }
    }

    public String getRealm(String realm){
        String url = GITHUB_URL + githubUsername + SLASH + githubRepository +
                SLASH + MAIN + SLASH + realm + SUFFIX;
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            if(json.equals("404: Not Found")){
                System.out.println(realm+" realm data not found on github.");
                return "";
            }
            System.out.println(realm+" realm data got from github.");
            return json;
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return "";
    }
}