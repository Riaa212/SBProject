package com.example.admin.proxy;

import java.time.LocalDate;

import com.example.admin.enums.GenderEnum;
import com.example.admin.enums.RoleEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProxy {

	private Integer id;
	private String userName;
	private LocalDate dob;
	private String password;
	private String email;
	private GenderEnum gender;
	private String address;
	private String mobileNumber;
	private String pinCode;
	
//	@Enumerated(EnumType.STRING)
	private RoleEnum Role;
	private String userImg;
	
}
