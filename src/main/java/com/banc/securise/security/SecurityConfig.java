package com.banc.securise.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// --- CRITICAL IMPORTS FOR CORS ---
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource; // This was the missing one!
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// ---------------------------------

import java.util.List;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

//                        .dispatcherTypeMatchers(jakarta.servlet.DispatcherType.FORWARD, jakarta.servlet.DispatcherType.ERROR).permitAll()

                              // allow preflight
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // public auth endpoints
        .requestMatchers("/api/v1/auth/login","/api/v1/auth/register").permitAll()                                
        .requestMatchers("/api/agentOauth/pending").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/client/operations").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/admin/users/**").hasRole("ADMIN")
                                .requestMatchers("/api/admin/users/desactiveUser/**").hasRole("ADMIN")
                                .requestMatchers("/api/agent/operations/**").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/admin/users/showAll").hasRole("ADMIN")
                                .requestMatchers("/api/admin/users/createUser").hasRole("ADMIN")
                                .requestMatchers("/api/handle/document/**").hasRole("AGENT_BANCAIRE")
                                .requestMatchers("/api/client/operations/").hasRole("CLIENT")
                                .requestMatchers("/api/v1/deposite/**").hasRole("CLIENT")
                                .requestMatchers("/api/v1/retrait/").hasRole("CLIENT")
                                .requestMatchers("/api/v1/transfer/").hasRole("CLIENT")
                                .requestMatchers("/api/v1/auth/userInfo").hasRole("CLIENT")
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


@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // EXACT origin (no wildcard with credentials)
    configuration.setAllowedOrigins(List.of("http://localhost:4200"));

    // Explicit methods
    configuration.setAllowedMethods(
        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
    );

    // Explicit headers (VERY IMPORTANT)
    configuration.setAllowedHeaders(
        List.of("Authorization", "Content-Type", "Accept")
    );

    // If you use JWT or cookies
    configuration.setAllowCredentials(true);

    // Optional but recommended
    configuration.setExposedHeaders(
        List.of("Authorization")
    );

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
    // expose authentication manager as a bean
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
