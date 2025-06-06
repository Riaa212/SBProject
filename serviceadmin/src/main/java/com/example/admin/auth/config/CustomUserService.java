package com.example.admin.auth.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.admin.domain.AdminEntity;
import com.example.admin.repository.AdminRepo;

@Component(value = "bean from custom user service")
public class CustomUserService implements UserDetailsService
{

	@Autowired
	private AdminRepo adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<AdminEntity> byEmail = adminRepo.findByEmail(email);
		
		
		if(byEmail==null)
		{
			throw new UsernameNotFoundException("user not found");	
		}
		
		return new CustomUser(byEmail.get());
	}

	
}
