package com.example.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServiceadminApplication {

	@Bean
	public RestTemplate restTemplate()
	{
	 return new RestTemplate();	
	}
	
//	 @Autowired
//	 private BCryptPasswordEncoder passwordEncoder;
	
	 
	public static void main(String[] args) {
		
		SpringApplication.run(ServiceadminApplication.class, args);
	}

}
