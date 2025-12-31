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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;

//    @Bean
//    @Order(1)
//    public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/api/agent/operations/pending")
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/agent/operations/pending").hasAuthority("SCOPE_operations.read")
//                        .requestMatchers("/debug").permitAll()
//                )
//                .oauth2ResourceServer(oauth2 ->
//                        oauth2.jwt(jwt ->
//                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
//                        )
//                );
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/auth/**", "/api/client/register", "/loginPage", "/templates/**", "/css/**", "/js/**","/api/v1/auth/register").permitAll()
                                .requestMatchers("/").authenticated()
                                .requestMatchers("/api/v1/auth/testRail").permitAll()
                                .requestMatchers("/api/agentOauth/pending").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/client/operations").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/admin/users/").hasRole("ADMIN")
                                .requestMatchers("/api/admin/users/desactiveUser/**").hasRole("ADMIN")
                                .requestMatchers("/api/agent/operations/**").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/admin/users/showAll").hasRole("ADMIN")
                                .requestMatchers("/api/admin/users/createUser").hasRole("ADMIN")
                                .requestMatchers("/api/handle/document/**").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/client/operations/").hasRole("CLIENT")
                                .requestMatchers("/api/v1/deposite/**").hasRole("CLIENT")
                                .requestMatchers("/api/v1/retrait/").hasRole("CLIENT")
                                .requestMatchers("/api/v1/transfer/").hasRole("CLIENT")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/loginPage")
//                        .loginProcessingUrl("/auth/login")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    @Order(1)
//    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/loginPage", "/css/**", "/js/**", "/images/**")
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .formLogin(form -> form
//                        .loginPage("/loginPage")
//                        .permitAll()
//                ).sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
//        ;
//
//        return http.build();
//    }



    // expose authentication manager as a bean
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
//
//        converter.setAuthoritiesClaimName("realm_access.roles");
//        converter.setAuthorityPrefix("ROLE_");
//
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//
//            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
//            if (realmAccess != null && realmAccess.containsKey("roles")) {
//                List<String> roles = (List<String>) realmAccess.get("roles");
//                authorities.addAll(
//                        roles.stream()
//                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                                .toList()
//                );
//            }
//            return authorities;
//        });
//
//        return jwtConverter;
//    }

}
