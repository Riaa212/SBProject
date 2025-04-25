package com.example.admin.utils;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class RecaptchaConfig {

//	 @Value("${recaptcha.secret-key}")
//	    private String secretKey;
//
//	    public String getSecretKey() {
//	        return secretKey;
//	    }
	
	 @Bean
	    public DefaultKaptcha kaptchaProducer() {
	        DefaultKaptcha kaptcha = new DefaultKaptcha();
	        Properties props = new Properties();

	        props.setProperty("kaptcha.border", "no");
	        props.setProperty("kaptcha.textproducer.char.length", "5");
	        props.setProperty("kaptcha.textproducer.font.color", "black");
	        props.setProperty("kaptcha.image.width", "200");
	        props.setProperty("kaptcha.image.height", "50");
	        props.setProperty("kaptcha.textproducer.font.size", "40");

	        kaptcha.setConfig(new Config(props));
	        return kaptcha;
	    }
}
