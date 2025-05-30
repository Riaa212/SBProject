package com.example.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.admin.domain.UserEntity;
import com.example.admin.proxy.UserProxy;

public interface UserService {

	
	public String registerUser(UserProxy user);
	
	public Page<UserEntity> getAllusers(Integer pageNumber, Integer pageSize);
	
	
	public UserProxy getUserById(Integer userId);
	
	
	public UserProxy updateUserById(UserProxy user,Integer id);
	
	public String deleteUserById(Integer id);
}
