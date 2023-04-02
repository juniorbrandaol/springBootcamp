package com.eblj.catalog.config;

import com.eblj.catalog.security.jwt.JwtAutFilter;
import com.eblj.catalog.security.jwt.JwtService;
import com.eblj.catalog.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Configuration
public class SecurityConfig  {

	private static final String[] PUBLIC ={"/api/users/auth","/api/users/save","/h2-console/**"};
	private static final String[] OPERATOR_OR_ADMIN ={"/api/products/**","/api/categories/**"};
	private static final String[]  ADMIN ={"/api/users/**","/api/products/**","/api/categories/**"};

	private static final String[] SWAGGER = {
			"/v2/api-docs/**",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**",
	};

    @Autowired
	private UserServiceImpl userServiceImpl;
   @Autowired
	private JwtService jwtService;
	@Autowired
	private Environment env;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> web.ignoring().requestMatchers(SWAGGER)
	                 	.requestMatchers(HttpMethod.OPTIONS,"/**");
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

		//H2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")){
			http.headers().frameOptions().disable();
			http.authorizeRequests()
			.requestMatchers("/h2-console/**").permitAll();
		}
		http

				.authorizeRequests()
				//se não vai usar uma aplicação web, apenas rest

				.requestMatchers(SWAGGER).permitAll()
				.requestMatchers (PUBLIC).permitAll()
			    .requestMatchers(HttpMethod.GET,OPERATOR_OR_ADMIN).permitAll()
				.requestMatchers(ADMIN).hasAnyRole("ADMIN","OPERATOR")
				.requestMatchers(ADMIN).hasRole("ADMIN")

			//	.anyRequest().authenticated()
				
				//.maximumSessions(1)
				//.expiredUrl("https://g1.globo.com/")
				//.and()
				.and().cors()
				.and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.cors().configurationSource(corsConfigurationSource())
				.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}



	/*Configura os host de acesso*/
	@Bean
	public CorsConfigurationSource  corsConfigurationSource(){
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("http://localhost:8000/api/v3/api-docs");
		corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers"));
		UrlBasedCorsConfigurationSource sourse = new UrlBasedCorsConfigurationSource();
		sourse.registerCorsConfiguration("/**",corsConfig);
		return sourse;
	}
    @Bean
	 public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean= new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	 }

}