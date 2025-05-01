package com.example.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.admin.domain.RefreshToken;
import com.example.admin.repository.AdminRepo;
import com.example.admin.repository.RefreshTokenRepo;

public class RefreshTockenServiceImpl {

	
	@Autowired
	private AdminRepo adminrepo;
	
	@Autowired
	private RefreshTokenRepo rsRepo;
	
	public RefreshToken createRSTocken(String username)
	{
		RefreshToken rst=new RefreshToken();
		rst.builder()
		.admin(adminrepo.findByEmail(username).get())
		.token(username).
		expiryDate(null)
		.build();
		return null;
	}
}
