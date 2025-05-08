package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
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
