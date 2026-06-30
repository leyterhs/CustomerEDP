package com.customeredp.config;

import com.customeredp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// Απενεργοποιούμε το CSRF (δεν το χρειαζόμαστε για stateless REST API)
			.csrf(csrf -> csrf.disable())
			
			// Ορίζουμε ποια endpoints είναι ανοιχτά (χωρίς authentication)
			.authorizeHttpRequests(authz -> authz
				// ΜΟΝΟ αυτά τα endpoints είναι προσβάσιμα χωρίς login
				.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
				// ΟΛΑ τα υπόλοιπα (συμπεριλαμβανομένου του Swagger) θέλουν authentication
				.anyRequest().authenticated()
			)
			
			// Λέμε στο Spring Security να χρησιμοποιεί το δικό μας service
			// για να φορτώνει τους χρήστες από τη Βάση
			.userDetailsService(customUserDetailsService)
			
			// Ενεργοποιούμε το Basic Authentication (username/password)
			.httpBasic(httpBasic -> {})
			
			// Δεν κρατάμε session (stateless) - κάθε request είναι ανεξάρτητο
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}