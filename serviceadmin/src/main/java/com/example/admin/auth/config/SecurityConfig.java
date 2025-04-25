package com.example.admin.auth.config;


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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private JwtEntryPoint entryPoint;
	
	@Autowired
	private CustomUserService userService;
	
	
	@Bean(name = "userDetails bean from config")
	public UserDetailsService userDetailsService()
	{
		System.out.println("user details service called..");
		return userService;
		//return new CustomUserService();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		System.out.println("security filter chain called..");
		http.csrf(csrf->csrf.disable());
		http.authorizeHttpRequests(auth->auth.requestMatchers(
				"/",
				"/user/register",
				"/admin/register",
				"/admin/loginReq",
				"/user/saveBluckstd/**",
				"/admin/resetpassword",
				"/admin/testOtp/**",
				"/admin/registerAdminwithImg",
				"/admin/login",
				"/admin/getCaptcha",
				"/user/downloadExcelFile"
//				"/user/loginReq",
//				"/user/testOtp/**",
//				"/user/resetpassword",
//				"/user/generateOtp/**"
				).permitAll().
				anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		http.exceptionHandling(auth->auth.authenticationEntryPoint(entryPoint));
		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder byBCryptPasswordEncoder()
	{
		System.out.println("bcryptPasswordEncoder called..");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider()
	{
		System.out.println("Authentication Provider called..");
		DaoAuthenticationProvider dap=new DaoAuthenticationProvider();
		
		dap.setUserDetailsService(userDetailsService());
		dap.setPasswordEncoder(byBCryptPasswordEncoder());
		return dap;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		System.out.println("authentication manager called.");
		return authenticationConfiguration.getAuthenticationManager();
	}
}
