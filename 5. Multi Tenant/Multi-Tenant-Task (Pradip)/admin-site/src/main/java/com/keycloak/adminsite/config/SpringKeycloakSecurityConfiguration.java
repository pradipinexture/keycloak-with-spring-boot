package com.keycloak.adminsite.config;

import com.keycloak.adminsite.service.KeycloakService;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class SpringKeycloakSecurityConfiguration {

    @DependsOn("keycloakConfigResolver")
    @KeycloakConfiguration
    @ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
    public static class KeycloakConfigurationAdapter extends KeycloakWebSecurityConfigurerAdapter {


        /**
         * Registers the KeycloakAuthenticationProvider with the authentication manager.
         */
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
            SimpleAuthorityMapper soa = new SimpleAuthorityMapper();
            keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(soa);
            auth.authenticationProvider(keycloakAuthenticationProvider);
        }

        /**
         * Defines the session authentication strategy.
         */
        @Bean
        @Override
        protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
            // required for bearer-only applications.
            // return new NullAuthenticatedSessionStrategy();
            return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
        }

        @Override
        protected AuthenticationEntryPoint authenticationEntryPoint() throws Exception {
            return new MultitenantKeycloakAuthenticationEntryPoint(adapterDeploymentContext());
        }

        @Override
        protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
            KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(authenticationManager(), new AntPathRequestMatcher("/tenant/*/sso/login"));
            filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
            return filter;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Bean
        public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(KeycloakAuthenticationProcessingFilter filter) {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
            registrationBean.setEnabled(false);
            return registrationBean;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Bean
        public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(KeycloakPreAuthActionsFilter filter) {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
            registrationBean.setEnabled(false);
            return registrationBean;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Bean
        public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(KeycloakAuthenticatedActionsFilter filter) {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
            registrationBean.setEnabled(false);
            return registrationBean;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Bean
        public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(KeycloakSecurityContextRequestFilter filter) {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
            registrationBean.setEnabled(false);
            return registrationBean;
        }

        @Bean
        @Override
        @ConditionalOnMissingBean(HttpSessionManager.class)
        protected HttpSessionManager httpSessionManager() {
            return new HttpSessionManager();
        }

        /**
         * Configuration spécifique à keycloak (ajouts de filtres, etc)
         * 
         * @param http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement()
                    .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                    .and().addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
                    .addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter.class).exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and().cors()
                    .and().logout().addLogoutHandler(keycloakLogoutHandler()).logoutUrl("/tenant/*/logout")
                    .logoutSuccessHandler(new LogoutSuccessHandler() {
                        @Override
                        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            response.sendRedirect("http://localhost:2222/tenant/"+ KeycloakService.currentRealmName +"/sso/login");
                        }
                    })
                    .and().apply(new SpringKeycloakSecurityAdapter());

        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList(HttpMethod.OPTIONS.name(), "GET", "POST"));
            configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Authorization"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }

    public static class SpringKeycloakSecurityAdapter extends AbstractHttpConfigurer<SpringKeycloakSecurityAdapter, HttpSecurity> {

        @Override
        public void init(HttpSecurity http) throws Exception {
            // any method that adds another configurer
            // must be done in the init method
            http
//                    .csrf().disable()
                    // disable csrf because of API mode
                    .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    // manage routes securisation here
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler())

                    .and().authorizeRequests().antMatchers("/error/**","/tenant/*/accessdenied","/tenant/*/logout","/tenant/*/sso/login","/favicon.ico").permitAll()
                    .antMatchers("/**").hasRole("admin")
                    .anyRequest().authenticated()
                    .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
//                    .and().exceptionHandling().authenticationEntryPoint(authEntryPoint);             // .antMatchers("/**/catalog").hasRole("CATALOG_MANAGER") //
        }
        @Bean
        public AccessDeniedHandler accessDeniedHandler(){
            return new CustomAccessDeniedHandler();
        }
    }
}