package com.example.admin.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.admin.domain.UserEntity;
import com.example.admin.enums.RoleEnum;
import com.example.admin.proxy.UserProxy;
import com.example.admin.repository.UserRepo;
import com.example.admin.service.UserService;
import com.example.admin.utils.ExcelHelper;
import com.example.admin.utils.Helper;
import com.github.javafaker.Faker;

@Service
public class UserServiceImpl implements UserService
{
	
	@Autowired	
	private Helper helper;

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public String registerUser(UserProxy user) {
		user.setRole(RoleEnum.User);
		UserEntity userObj = helper.convert(user, UserEntity.class);
		userRepo.save(helper.convert(user, UserEntity.class));
		return "user Register successfully..";
	}

	
	
	//save bluck of users
	public String saveBulkOfUsers(Integer noOfUsers) {

		for(int i=0;i<noOfUsers;i++)
		{
//			studentRepository.save(generateStudent());
			userRepo.save(generateUsers());
		}
		return null;
	}
	
	//generate users
	private UserEntity generateUsers()
	{
		
//	String profileImageUrl = "https://picsum.photos/200/300?random=";
//		String profileImageUrl="https://avatar.iran.liara.run/public?random=";
	String profileImageUrl="https://picsum.photos/200/300?random=";	
	
	LocalDateTime curreDateTime=LocalDateTime.now();
	Faker f=new Faker();
	UserEntity user=new UserEntity();
	user.setUserName(f.name().fullName());
	user.setEmail(f.name().firstName()+"@gmail.com");
	Date fakeDob = f.date().birthday(18, 60); // age range: 18 to 60
    LocalDate dob = fakeDob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	user.setDob(dob);
	user.setRole(RoleEnum.User);
//	user.setIsActive(false);
	user.setAddress(f.address().fullAddress());
	user.setPinCode(f.country().countryCode2());
	user.setIsActive(true);
	user.setMobileNumber(f.phoneNumber().phoneNumber());
//	f.avatar().image().to
//	user.setUserImg(profileImageUrl);
	user.setUserImg(profileImageUrl+f.number().randomNumber());
	return user;
	}
	
	
	public String deleteAllUsers()
	{
	userRepo.deleteAll();
	return 	"deleted succssfully..";
	}
	
	@Override
	public Page<UserEntity> getAllusers(Integer pageNumber, Integer pageSize) {
//		List<UserEntity> userEntity = userRepo.findAll();
//		return helper.convertList(userEntity, UserProxy.class);
		Page<UserEntity> page = userRepo.findAll(PageRequest.of(pageNumber, pageSize));
//		System.err.println(page.stream().for);
//		List<UserEntity> byUserName = userRepo.findByUserName(userName);
		System.err.println("page===>"+page+"\nPage Number==>"+pageNumber+"\nPage Size==>"+pageSize);
		return page;
	}


	public Page<UserEntity> searchByName(String userName,Integer pageNumber, Integer pageSize) {
		
		Page<UserEntity> all = userRepo.findAll(PageRequest.of(pageNumber, pageSize));
		
	   Page<UserEntity> byUserName = userRepo.findByUserNameContainingIgnoreCase(userName,PageRequest.of(pageNumber, pageSize));
		
		return byUserName;
	}
	
	
	@Override
	public UserProxy getUserById(Integer userId) {
		Optional<UserEntity> byId = userRepo.findById(userId);
		if(byId.isPresent())
		{
		UserEntity userEntity = byId.get();	
		
		return helper.convert(userEntity, UserProxy.class);
		}
		return null;
	}

	@Override
	public UserProxy updateUserById(UserProxy user, Integer userid) {
		Optional<UserEntity> byId = userRepo.findById(userid);
		if(byId.isPresent())
		{
			UserEntity userEntity = byId.get();
			userEntity.setUserName(user.getUserName());
			userEntity.setEmail(user.getEmail());
//			userEntity.setDob(user.getDob());
			userEntity.setGender(user.getGender());
			userEntity.setPinCode(user.getPinCode());
			userEntity.setAddress(user.getAddress());
			userRepo.save(userEntity);
			System.err.println(userEntity);
		}
		return user;
	}

	@Override
	public String deleteUserById(Integer id) {
		
		userRepo.deleteById(id);
		return "user deleted successfully";
	}

	
	//download excel file
	public ByteArrayOutputStream downloadExcelFile() throws IOException //working
	{
		List<UserEntity> emps=userRepo.findAll();
		
		ByteArrayOutputStream empToExcel = ExcelHelper.empToExcel(emps);
		
		return empToExcel;
	}
}
