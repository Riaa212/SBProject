package com.example.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.domain.RefreshToken;

public interface RefreshTokenRepo  extends JpaRepository<RefreshToken,Integer>
{

}
