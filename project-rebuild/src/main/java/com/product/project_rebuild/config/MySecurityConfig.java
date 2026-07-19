package com.product.project_rebuild.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Autowired
	private UserDetailsService userDtailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		return http.csrf(c -> c.disable())
			.authorizeHttpRequests(req -> req
					.requestMatchers("/add_user", "/login")
					.permitAll().anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // before UsernamePasswordAuthenticationFilter add the jwtFilter
			.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuPr = new DaoAuthenticationProvider(userDtailsService);
		daoAuPr.setPasswordEncoder(new BCryptPasswordEncoder(8));
		return daoAuPr;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) {
		return conf.getAuthenticationManager();
	}
	
}
