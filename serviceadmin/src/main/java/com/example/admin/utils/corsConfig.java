package com.example.admin.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class corsConfig implements WebMvcConfigurer {

	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // apply to all paths
	                .allowedOrigins("http://localhost:3000") // your frontend origin
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowedHeaders("*")
	                .allowCredentials(true); // if you need cookies or auth
	    }
	
}
