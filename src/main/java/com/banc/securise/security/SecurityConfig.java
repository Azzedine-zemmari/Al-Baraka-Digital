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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

//                        .dispatcherTypeMatchers(jakarta.servlet.DispatcherType.FORWARD, jakarta.servlet.DispatcherType.ERROR).permitAll()

                                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/webjars/**","/error").permitAll()
                                .requestMatchers("/").authenticated()
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


//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .permitAll()
//                )


//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(new org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint("/login"))
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    // expose authentication manager as a bean
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
