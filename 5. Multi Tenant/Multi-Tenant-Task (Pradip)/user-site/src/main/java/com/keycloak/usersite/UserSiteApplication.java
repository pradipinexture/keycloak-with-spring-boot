package com.keycloak.usersite;

import com.keycloak.usersite.config.PathBasedConfigResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSiteApplication.class, args);
	}
	@Bean
	@ConditionalOnMissingBean(PathBasedConfigResolver.class)
	public KeycloakConfigResolver keycloakConfigResolver() {
		return new PathBasedConfigResolver();
	}
}
