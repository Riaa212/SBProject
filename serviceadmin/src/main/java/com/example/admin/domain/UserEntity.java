package com.example.admin.domain;

import java.util.Date;

import com.example.admin.enums.GenderEnum;
import com.example.admin.enums.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private Date dob;
//	private String password;
	private GenderEnum gender;
	private String address;
	private String email;
	private String mobileNumber;
	private String pinCode;
	private String userImg;
//	  @Enumerated(EnumType.STRING)
	private RoleEnum Role;
//	public void setMobileNumber(PhoneNumber phoneNumber) {
//		// TODO Auto-generated method stub
//		
//	}
}

