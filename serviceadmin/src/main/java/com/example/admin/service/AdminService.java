package com.example.admin.service;

import com.example.admin.domain.AdminEntity;
import com.example.admin.domain.LoginRequest;
import com.example.admin.domain.LoginResponse;
import com.example.admin.proxy.AdminProxy;

public interface AdminService {

	//register admin
	public String RegisterAdmin(AdminProxy admin);
	
	//get adminbyid
	public AdminProxy getAdminById(Integer id);
	
	//login admin
	public LoginResponse login(LoginRequest loginRequest);

	public String updateAdminById(Integer id,AdminProxy admin);
	
	public AdminProxy getAdminByEmail(String email);
	
	
	
}
