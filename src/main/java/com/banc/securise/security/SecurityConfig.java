package com.banc.securise.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sm->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/v1/auth/register").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/admin/users/").hasRole("ADMIN")
                                .requestMatchers("/api/admin/users/showAll").hasRole("ADMIN")
                                .requestMatchers("/api/admin/users/createUser").hasRole("ADMIN")
                                .requestMatchers("/api/v1/deposite/active/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/deposite/cancel/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/retrait/active/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/retrait/cancel/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/transfer/active/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/transfer/cancel/**").hasRole("ADMIN")
                                .requestMatchers("/api/client/operations/pending").hasRole("ADMIN")
                                .requestMatchers("/api/client/operations/").hasRole("ADMIN")
                                .requestMatchers("/api/v1/deposite/**").hasRole("CLIENT")
                                .requestMatchers("/api/v1/retrait/").hasRole("CLIENT")
                                .requestMatchers("/api/v1/transfer/").hasRole("CLIENT")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    @Order(1)
//    public SecurityFilterChain keycloakSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/api/agentOauth/**")
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/api/agentOauth/operations/**")
//                                .hasAuthority("SCOPE_operations.read")
//                                .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 ->
//                        oauth2.defaultSuccessUrl("/api/agentOauth", true)
//                );
//        return http.build();
//    }

    // expose authentication manager as a bean
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
