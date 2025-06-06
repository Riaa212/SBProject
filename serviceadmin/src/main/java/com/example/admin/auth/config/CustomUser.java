package com.example.admin.auth.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.admin.domain.AdminEntity;

public class CustomUser implements UserDetails
{
	private static final long serialVersionUID = 1L;

	@Autowired
	private AdminEntity admin;
	
	public CustomUser(AdminEntity admin) {
		super();
		this.admin=admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(admin.getRole());
		return Arrays.asList(simpleGrantedAuthority);
	}
	
	

	@Override
	public String getPassword() {
			
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		return admin.getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}


}
