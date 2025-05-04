package com.example.admin.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfig  implements WebMvcConfigurer
{

	 @Override
	   public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")  // Allow all endpoints
	                .allowedOrigins("http://localhost:4200") // Allow Angular frontend
	                .allowedMethods("*")
	                .allowedHeaders("*")
	                .allowCredentials(true);
}
	  
//	  @Override
//	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	  	
//	  	String currentDir = System.getProperty("user.dir");
//	      String uploadPath = "file:///" + currentDir.replace("\\", "/") + "/uploads/";
//
//	      registry.addResourceHandler("/uploads/**")
//	              .addResourceLocations(uploadPath);
//
//	      System.out.println("Serving uploads from: " + uploadPath); // 👈 Debug line
//	  }
//	  
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/static/adminProfile/**")
	                .addResourceLocations("file:uploads/adminProfile/");
	    }
}
