package com.eblj.catalog.config;

import com.eblj.catalog.security.jwt.JwtAutFilter;
import com.eblj.catalog.security.jwt.JwtService;
import com.eblj.catalog.servicies.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig  {

    @Autowired
	private UserServiceImpl userServiceImpl;

   @Autowired
	private JwtService jwtService;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
	}
	@Bean
	public OncePerRequestFilter jwtFilter(){
		return new JwtAutFilter(jwtService,userServiceImpl);
	}

	@Bean
	public UserDetailsService userDetailsService(AuthenticationManagerBuilder aut) throws Exception {
		aut
				.userDetailsService(userServiceImpl)
				.passwordEncoder(passwordEncoder());
		return aut.getDefaultUserDetailsService();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				//se não vai usar uma aplicação web, apenas rest
				.cors().and().csrf().disable()
				.authorizeRequests()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers("/api/categories/**").hasRole("OPERATOR")
				.requestMatchers("/api/products/**").hasRole("ADMIN")
				.requestMatchers("/api/users/**").hasRole("OPERATOR")
				//.requestMatchers(AUTH_WHITELIST).permitAll()
				.requestMatchers(HttpMethod.POST,"/api/users/**").permitAll()
				.anyRequest().authenticated()//garante que se esquecer de mapear outra api, o minimo de autenticação
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				//.and().headers().frameOptions().sameOrigin()
				.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	private static final String[] AUTH_WHITELIST = {
			"/h2-**",
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",

			"/swagger-ui.html",
			"/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**",
            "/**"
	};

	
}