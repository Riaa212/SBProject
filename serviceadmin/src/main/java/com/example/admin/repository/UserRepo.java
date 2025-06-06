package com.example.admin.repository;
 
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.admin.domain.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer>
{

	Page<UserEntity> findAll(Pageable page);
	
	Page<UserEntity> findByUserNameContainingIgnoreCase(String name,Pageable page);
//	@Query("Select c from Registration c where c.place like :place")
//    List<Registration> findByPlaceContaining(@Param("place")String place);
	
//	@Query("Select c from user c where c.userName like :userName")
//	List<UserEntity> findByUserNameContaining(@Param("userName")String userName);
	
//	@Query("Select c from user c where c.user_name like :userName")
//	List<UserEntity> findByUserNameContaining(@Param("userName")String userName);
	
//	List<UserEntity> findByUserName(String name);
	
	List<UserEntity> findByUserNameLike(String name);
	
	List<UserEntity> findByUserNameOrEmail(String username,String email);
}
