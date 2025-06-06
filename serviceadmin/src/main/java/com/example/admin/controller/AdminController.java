package com.example.admin.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.admin.domain.AdminEntity;
import com.example.admin.domain.EmailDetails;
import com.example.admin.domain.LoginRequest;
import com.example.admin.domain.LoginResponse;
import com.example.admin.proxy.AdminProxy;
import com.example.admin.repository.AdminRepo;
import com.example.admin.service.impl.AdminServiceImpl;
import com.example.admin.service.impl.PdfService;
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
	
	 @Autowired
	 private AdminRepo repo;
	 
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	 
	 
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

	@PostMapping("/testOtp") //working for sent otp
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
	 
//	 private static final String UPLOAD_DIR = "adminProfile/";

	 //register admin
	    @PostMapping("/upload")
	    public String uploadImage(
	    		@RequestParam("username") String username,
	    		@RequestParam("email") String email,
	    		@RequestParam("password")String password, 
	    		@RequestParam("image") MultipartFile file) throws IOException {

	    	
	    	  try {
//	  	    	System.err.println(email+"\n"+username+"\n"+file);
	              // Folder: uploads/adminProfile
//	              String folderPath = "upload/adminProfile";
//	              File dir = new File(folderPath);
//	              if (!dir.exists()) {
//	                  boolean created = dir.mkdirs();
//	                  System.out.println("Directory created: " + created);
//	              }
	    		  
	    		// Get absolute path to the project root
	    		  String projectRoot = new File(".").getCanonicalPath();
	    		  String folderPath = projectRoot + File.separator + "uploads" + File.separator + "adminProfile";
	    		  File uploadDir = new File(folderPath);

	    		  // Create folder if it doesn't exist
	    		  if (!uploadDir.exists()) {
	    		      boolean created = uploadDir.mkdirs();
	    		      System.out.println("Created folder: " + created);
	    		  }

	    		  
	              // Unique filename
	              String originalName = file.getOriginalFilename();
	              String extension = originalName.substring(originalName.lastIndexOf("."));
	              String uniqueName = UUID.randomUUID() + extension;

	              File destination = new File(uploadDir, uniqueName);
	              file.transferTo(destination); // save file

	              // Build URL
	              String imageUrl = "http://localhost:2424/static/adminProfile/" + uniqueName;

	              // Save to DB
	              AdminEntity image = new AdminEntity();
//	              image.setUsername(username);
//	              image.setImageUrl(imageUrl);
	              image.setUserName(username);
	              image.setEmail(email);
	              image.setRole("Admin");
	              image.setPassword(passwordEncoder.encode(password));
	              image.setProfilePic(imageUrl);
	              
	              repo.save(image);

	              return "Uploaded successfully: " + imageUrl;

	          } catch (IOException e) {
	              e.printStackTrace();
	              return "Error: " + e.getMessage();
	          }

	    }
	 
	    //update admin
//	    ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	    @PostMapping(value="/updateProfile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public String updateAdminById(
				@RequestParam("image") MultipartFile file) 
		
		{
	    	String filetype=file.getContentType();
//	    	System.err.println(file.getContentType());
	    	Integer	aid=1;
	    	Optional<AdminEntity> byId = repo.findById(aid);
	    	
	    	
	    	
	    	System.err.println(byId);
			if(byId.isPresent())
			{
				AdminEntity adminEntity = byId.get();
				
				try {
				String projectRoot = new File(".").getCanonicalPath();
	    		  String folderPath = projectRoot + File.separator + "uploads" + File.separator + "adminProfile";
	    		  File uploadDir = new File(folderPath);
	    		  // Unique filename
	              String originalName = file.getOriginalFilename();
	              String extension = originalName.substring(originalName.lastIndexOf("."));
	              String uniqueName = UUID.randomUUID() + extension;

	              File destination = new File(uploadDir, uniqueName);
	              file.transferTo(destination); // save file

	              // Build URL
	              String imageUrl = "http://localhost:2424/static/adminProfile/" + uniqueName;
				  System.err.println("upload======="+imageUrl);
				 
				  adminEntity.setProfilePic(imageUrl);
				  
				  repo.save(adminEntity);
				return imageUrl;
				}
				catch (IOException e) {
		              e.printStackTrace();
		          }
			}
			  return filetype;
		}
	    
	    
	    
	    @PostMapping(path="/updateProfileData")
		public AdminEntity updateAdminProfileData(@RequestBody AdminEntity admin) 
		{
//	    	System.err.println("email--"+email+"password---"+username);
	    	Integer	aid=1;
	    	Optional<AdminEntity> byId = repo.findById(aid);
	    	
	    	
	    	System.err.println(byId);
			if(byId.isPresent())
			{
				AdminEntity adminEntity = byId.get();
				  adminEntity.setUserName(admin.getUserName());
				  adminEntity.setEmail(admin.getEmail());
				  System.err.println(adminEntity);
				  repo.save(adminEntity);
				return adminEntity;
			}
			  return byId.get();
		}
	    
	    //download pdf
	    
	    
	    
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
