package com.example.admin.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.admin.auth.config.JwtService;
import com.example.admin.domain.AdminEntity;
import com.example.admin.domain.EmailDetails;
import com.example.admin.domain.LoginRequest;
import com.example.admin.domain.LoginResponse;
import com.example.admin.enums.RoleEnum;
import com.example.admin.proxy.AdminProxy;
import com.example.admin.repository.AdminRepo;
import com.example.admin.service.AdminService;
import com.example.admin.utils.Helper;

import io.jsonwebtoken.io.IOException;

@Service
public class AdminServiceImpl implements AdminService
{
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired 
	private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") 
    private String sender;
	
	@Autowired
	private Helper helper;
	
	@Override
	public LoginResponse login(LoginRequest loginRequest)
	{
		//System.out.println("login response from emp service called..");
		Authentication authentication=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		
		Authentication verified = authmanager.authenticate(authentication);
		
		//verified.getAuthorities().stream().forEach(a->System.out.println("Authorities:"+a));
		if(!verified.isAuthenticated())
		{
			System.out.println("bad credials..");
			//throw new ErrorResponse("bad credentials",404);
		}
		System.err.println("authenticated....."+loginRequest.getEmail()+"\n"+loginRequest.getPassword());
		 return new LoginResponse(loginRequest.getEmail(),jwtService.genearteTocken(loginRequest.getEmail()),(List<SimpleGrantedAuthority>) verified.getAuthorities());
	}

	@Override
	public String RegisterAdmin(AdminProxy admin) {
		
		admin.setRole(RoleEnum.Admin);
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		
		AdminEntity adminEntity = helper.convert(admin, AdminEntity.class);
		
		adminRepo.save(adminEntity);
		
		return "admin register successfully";
	}

	@Override
	public AdminProxy getAdminById( Integer id) {
		List<AdminEntity> all = adminRepo.findAll();
		return helper.convert(all, AdminProxy.class);
	}

	@Override
	public String updateAdminById(Integer id,AdminProxy admin) {
		Optional<AdminEntity> byId = adminRepo.findById(id);
		if(byId.isPresent())
		{
			AdminEntity adminEntity = byId.get();
			adminEntity.setEmail(admin.getEmail());
			adminEntity.setUserName(admin.getUserName());
			adminRepo.save(adminEntity);
		}
		return null;
	}

	@Override
	public AdminProxy getAdminByEmail(String email) {
		Optional<AdminEntity> byEmail = adminRepo.findByEmail(email);
		
		if(byEmail.isPresent())
		{
			AdminEntity adminEntity = byEmail.get();
			return helper.convert(adminEntity,AdminProxy.class);
		}
		return null;
	}
	
	public String testOtp(EmailDetails details)
	{
		System.err.println("generate otp method............");
		String otp=null;
		Optional<AdminEntity> byEmail = adminRepo.findByEmail(details.getRecipient());
		if(byEmail.isPresent())
		{
			AdminEntity adminobj=byEmail.get();
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
			System.out.println("OTP===>"+otp);
			
			   mailMessage.setFrom(sender);
	           mailMessage.setTo(details.getRecipient());
	           mailMessage.setText("Your otp is.."+otp);
	           mailMessage.setSubject(details.getSubject());
	           javaMailSender.send(mailMessage);
	           adminobj.setOtp(otp);
				adminRepo.save(adminobj);
			System.out.println("OTP===>"+otp);	
//			adminRepo.save(adminobj);
		}
		return otp;
	}
	
	
	public String sendSimpleMail(EmailDetails details) {
		
		
		 // Try block to check for exceptions
       try {

           // Creating a simple mail message
           SimpleMailMessage mailMessage
               = new SimpleMailMessage();

           // Setting up necessary details
           mailMessage.setFrom(sender);
           mailMessage.setTo(details.getRecipient());
           mailMessage.setText(details.getMsgBody());
           mailMessage.setSubject(details.getSubject());
           // Sending the mail
           javaMailSender.send(mailMessage);
           return "Mail Sent Successfully...";
       }

       // Catch block to handle the exceptions
       catch (Exception e) {
       	System.err.println(e);
           return "Error while Sending Mail";
       }
	}
	
	public String forgetPwd(AdminEntity admin)
	{
		System.err.println("forget password calledd........");
		Optional<AdminEntity> byEmail = adminRepo.findByEmail(admin.getEmail());
	
//		EmailDetails details=new EmailDetails();
		
		if(byEmail.isPresent())
		{
			Optional<AdminEntity> byOtp = adminRepo.findByOtp(admin.getOtp());
			
			if(byOtp.isPresent())
			{				
//				System.err.println("in otp");
//				 SimpleMailMessage mailMessage= new SimpleMailMessage();
//				 mailMessage.setFrom(sender);
//		         mailMessage.setTo(recipient);
//		         mailMessage.setText(msg);
//		         mailMessage.setSubject(subject);
				 
//		         javaMailSender.send(mailMessage);
		         
				//update password
				System.err.println("update....................."+byOtp.get().getEmail());
				
				byOtp.get().setPassword(passwordEncoder.encode(admin.getPassword()));
				
				System.err.println("by otp===>"+byOtp);
				
				adminRepo.save(byOtp.get());
				
				System.err.println("after saved successfully.....");
				
				return "password updated successfully";
			}
		}
		return "Something went wrong!!!!";
	}
	
	

	public AdminEntity createadmin(List<MultipartFile> images,AdminEntity adminEntity)
	{
		
	    List<String> imageUrls = new ArrayList<>();
	    
	    for (MultipartFile image : images) {
	        try {
	        	//for unique file name
	        	UUID uuid=UUID.randomUUID();
	            String fileName =  uuid+ "_" + image.getOriginalFilename();
	            
	            //upload in folder
	            Path path = Paths.get("uploads/" + fileName);
	            
	            Files.write(path, image.getBytes());

	            	
	            String imageUrl= "http://localhost:2424/uploads/" + fileName;
	      
	            imageUrls.add(imageUrl);
	            
//	            System.err.println("inside==="+imageUrl);
//	            
//	            System.err.println("Image saved to: " + path.toAbsolutePath());
//	            
	            // Get just the file part from the URL
//	            String filePart = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

	            // Now get the part after the last underscore
//	            String finalFileName = filePart.substring(filePart.lastIndexOf('_') + 1);
//
//	            System.err.println("image url===>"+finalFileName);  // Output: place.jpg
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    AdminEntity admin = new AdminEntity();
	    admin.setEmail(adminEntity.getEmail());
	    admin.setUserName(adminEntity.getUserName());
	    admin.setPassword(passwordEncoder.encode(adminEntity.getPassword()));
	    admin.setRole(RoleEnum.Admin.toString());
	    System.err.println(imageUrls);
//	    admin.setImageUrl(imageUrls);
//	    adminEntity.setImageUrl(imageUrls);
	    
	    AdminEntity saved = adminRepo.save(admin);
	    
	    
	    return saved;
	}
	
}
