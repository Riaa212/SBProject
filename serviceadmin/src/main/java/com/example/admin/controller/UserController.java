package com.example.admin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.proxy.UserProxy;
import com.example.admin.repository.UserRepo;
import com.example.admin.service.impl.PdfService;
import com.example.admin.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserServiceImpl service;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PdfService pdfService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserProxy user)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(user));
	}
	
	@GetMapping("/saveBluckstd/{id}")
	public String saveStudents(@PathVariable("id") Integer id)
	{
		service.saveBulkOfUsers(id);
		return "saved";
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUser(@RequestParam(value="page",defaultValue = "0") Integer pageNumber,@RequestParam(value = "size",defaultValue = "10") Integer pageSize)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.getAllusers(pageNumber,pageSize));
	}
	
	@GetMapping("/getUsername/{name}")
	public ResponseEntity<?> searchUser(@PathVariable("name") String name,@RequestParam(value="page",defaultValue = "0") Integer pageNumber,@RequestParam(value = "size",defaultValue = "10") Integer pageSize)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.getAllusers(pageNumber,pageSize));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteAllUsers()
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.deleteAllUsers());
	}
	
	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<?> deleteuserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.deleteUserById(id));
	}
	
	@PutMapping("/updateUserById/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable("id") Integer id,@RequestBody UserProxy user)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.updateUserById(user, id));
	}

	@GetMapping("/getUserById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.getUserById(id));
	}
	
	@GetMapping("/getUserByName/{name}")
	public ResponseEntity<?> getUserByName(@PathVariable("name") String username,@RequestParam(value="page",defaultValue = "0") Integer pageNumber,@RequestParam(value = "size",defaultValue = "10") Integer pageSize)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.searchByName(username, pageNumber,pageSize));
	}

	@GetMapping("/downloadExcelFile") //working
	  public ResponseEntity<byte[]> getFile() throws IOException {
		 
		  String fileName="userdata.xlsx";
	     return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
	        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(service.downloadExcelFile().toByteArray());
	  }
	  
	    @GetMapping("/downloadpdf")
	    public ResponseEntity<InputStreamResource> downloadPdf() {
	    	 ByteArrayInputStream bis = pdfService.createPdfWithTable();

	         HttpHeaders headers = new HttpHeaders();
	         headers.add("Content-Disposition", "inline; filename=users.pdf");

	         return ResponseEntity.ok()
	                 .headers(headers)
	                 .contentType(MediaType.APPLICATION_PDF)
	                 .body(new InputStreamResource(bis));
	     }
	  
	  @GetMapping("/getUserByEailAndUserName/{username}/{email}")
	  public ResponseEntity<?> getByUsernameandemail(@PathVariable("username") String username,
			  @PathVariable("email") String email)
	  {
		  return ResponseEntity.status(HttpStatus.OK).body(repo.findByUserNameOrEmail(username, email));
	  }
}
