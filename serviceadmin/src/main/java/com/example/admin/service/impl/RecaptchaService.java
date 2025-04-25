package com.example.admin.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpSession;

@Service
public class RecaptchaService {

//	 @Autowired
//	    private RestTemplate restTemplate;
//	 
//	 @Autowired
//	 private RecaptchaConfig reCaptchaConfig;
//	 
//	 public boolean verify(String response) {
//	        String url = "https://www.google.com/recaptcha/api/siteverify";
//	        String params = "?secret=" + reCaptchaConfig.getSecretKey() + "&response=" + response;
//	        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(url + params, ReCaptchaResponse.class);
//	        return reCaptchaResponse != null && reCaptchaResponse.isSuccess();
//	    }
//
//	    static class ReCaptchaResponse {
//	      private boolean success;
//
//	        public boolean isSuccess() {
//	            return success;
//	        }
//
//	        public void setSuccess(boolean success) {
//	            this.success = success;
//	        }
//	    }
	
	@Autowired
    private DefaultKaptcha kaptchaProducer;

    public Map<String, String> createCaptcha(HttpSession session) throws java.io.IOException {
        String captchaText = kaptchaProducer.createText();
        BufferedImage captchaImage = kaptchaProducer.createImage(captchaText);

        // Store the CAPTCHA text in session
        session.setAttribute("captcha", captchaText);

        // Convert image to Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(captchaImage, "png", baos);
            String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

            return Map.of("image", "data:image/png;base64," + base64Image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CAPTCHA image", e);
        }
    }
}


