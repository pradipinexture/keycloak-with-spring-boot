package com.keycloak.adminsite;

import com.keycloak.adminsite.config.PathBasedConfigResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminSiteApplication.class, args);
	}
	@Bean
	@ConditionalOnMissingBean(PathBasedConfigResolver.class)
	public KeycloakConfigResolver keycloakConfigResolver() {
		return new PathBasedConfigResolver();
	}
}
