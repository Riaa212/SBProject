package com.example.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.admin.domain.AdminEntity;
import com.example.admin.domain.EmailDetails;
import com.example.admin.domain.LoginRequest;
import com.example.admin.domain.LoginResponse;
import com.example.admin.proxy.AdminProxy;
import com.example.admin.service.impl.AdminServiceImpl;
import com.example.admin.service.impl.RecaptchaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	private AdminServiceImpl service;
	
	 @Autowired
	    private RecaptchaService reCaptchaService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerAdmin(@RequestBody AdminProxy admin)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.RegisterAdmin(admin));
	}
	
	@GetMapping("/getAdminById/{id}")
	public ResponseEntity<?> getAdminById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.getAdminById(id));
	}
	
	@GetMapping("/getAdminByEmail/{email}")
	public ResponseEntity<?> getAdminByEmail(@PathVariable("email") String email)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.getAdminByEmail(email));
	}
	
	@PostMapping("/loginReq")
	public LoginResponse login(@RequestBody LoginRequest loginRequest)
	{
//		System.out.println(environment.getProperty("local.server.port"));
//		if (reCaptchaService.verify(loginRequest.getCaptcha())) {
//			System.out.println(loginRequest.getPassword()+"\n"+loginRequest.getEmail()+"\n"+loginRequest.getCaptcha());	
//			return service.login(loginRequest);
//		}
		return service.login(loginRequest);
	}
	
	@PutMapping("/updateAdmin/{id}")
	public ResponseEntity<?> updateAdminById(@PathVariable("id") Integer id,@RequestBody AdminProxy admin)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.updateAdminById(id,admin));
	}
	
	@PostMapping("/resetpassword") //working for reset password -forget password
	public ResponseEntity<?> generateOtp(@RequestBody AdminEntity user)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.forgetPwd(user));
	}

	@GetMapping("/testOtp") //working for sent otp
	public String testOtp(@RequestBody EmailDetails emailDetails)
	{
		
		return service.testOtp(emailDetails);
	}
	
	 @GetMapping("/getCaptcha")
	    public ResponseEntity<Map<String, String>> getCaptcha(HttpSession session) throws IOException {
	        return ResponseEntity.ok(reCaptchaService.createCaptcha(session));
	    }
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
	    String expectedCaptcha = (String) session.getAttribute("captcha");

	    if (expectedCaptcha == null || !expectedCaptcha.equalsIgnoreCase(loginRequest.getCaptcha())) {
	        return ResponseEntity.badRequest().body("Invalid CAPTCHA");
	    }
	    // Proceed with login logic...
	    return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.login(loginRequest));
	}
	
	 @PostMapping("/registerAdminwithImg")
	 public ResponseEntity<?> RegisterAdmin(
//	     @RequestPart("title") String title,
//	     @RequestPart("content") String content,
//	     @RequestPart("") String category
		@ModelAttribute("blog") AdminEntity adminEntity,
	     @RequestPart("images") List<MultipartFile> images
	 ) {
		 System.err.println("image upload controller....");
	     return ResponseEntity.status(HttpStatus.OK).body(service.createadmin(images, adminEntity));
	 }
	 

//	    @PostMapping("/submitForm")
//	    public ResponseEntity<String> submitForm(@RequestParam String name, @RequestParam String email, @RequestParam String recaptchaResponse) {
//	        if (reCaptchaService.verify(recaptchaResponse)) {
//	            // Process form data if reCAPTCHA is valid
//	            return ResponseEntity.ok("Form submitted successfully!");
//	        } else {
//	            // Handle invalid reCAPTCHA
//	            return ResponseEntity.badRequest().body("Invalid reCAPTCHA. Please try again.");
//	        }
//	    }
}
