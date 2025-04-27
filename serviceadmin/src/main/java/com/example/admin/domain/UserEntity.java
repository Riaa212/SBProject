package com.example.admin.domain;

import java.time.LocalDate;

import com.example.admin.enums.GenderEnum;
import com.example.admin.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name="user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String userName;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	
//	private String password;
	private GenderEnum gender;
	private String address;
	private String email;
	private String mobileNumber;
	private String pinCode;
	private String userImg;

	@Enumerated(EnumType.STRING)
	private RoleEnum Role;
	
	private Boolean isActive=false;
//	public void setMobileNumber(PhoneNumber phoneNumber) {
//		// TODO Auto-generated method stub
//		
//	}
}

