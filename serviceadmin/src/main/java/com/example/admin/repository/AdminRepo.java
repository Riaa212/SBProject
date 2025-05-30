package com.example.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.domain.AdminEntity;

public interface AdminRepo extends JpaRepository<AdminEntity,Integer>
{

	
	Optional<AdminEntity> findByEmail(String email);
	
	Optional<AdminEntity> findByOtp(String otp);
}
