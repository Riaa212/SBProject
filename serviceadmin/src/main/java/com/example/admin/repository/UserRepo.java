package com.example.admin.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.admin.domain.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer>
{

	Page<UserEntity> findAll(Pageable page);
}
