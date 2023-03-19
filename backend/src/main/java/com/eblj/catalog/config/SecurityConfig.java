package com.eblj.catalog.config;

import com.eblj.catalog.security.jwt.JwtAutFilter;
import com.eblj.catalog.security.jwt.JwtService;
import com.eblj.catalog.servicies.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;
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

import java.util.Arrays;


@Configuration
public class SecurityConfig  {

	private static final String[] PUBLIC ={"/api/users/auth","/api/users/save","/h2-console/**"};
	private static final String[] OPERATOR_OR_ADMIN ={"/api/products/**","/api/categories/**"};
	private static final String[]  ADMIN ={"/api/users/**","/api/products/**","/api/categories/**"};

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
     @Autowired
     private Environment env;

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

		//H2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")){
			http.headers().frameOptions().disable();

			http.authorizeRequests()

			.requestMatchers("/h2-console/**").permitAll()
					.and()
					.cors().and().csrf().disable();
		}
		http
				.csrf().disable()
				.authorizeRequests()
				//se não vai usar uma aplicação web, apenas rest


				//.requestMatchers("/").permitAll()
				.requestMatchers (PUBLIC).permitAll()
			    .requestMatchers(HttpMethod.GET,OPERATOR_OR_ADMIN).permitAll()
				.requestMatchers(ADMIN).hasAnyRole("ADMIN","OPERATOR")
				.requestMatchers(ADMIN).hasRole("ADMIN")



				//.maximumSessions(1)
				//.expiredUrl("https://g1.globo.com/")
				//.and()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	private static final String[] AUTH_WHITELIST = {
			"/h2-console/**",
			"/h2-**",
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/swagger-ui.html",
			"/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**"
	};


}