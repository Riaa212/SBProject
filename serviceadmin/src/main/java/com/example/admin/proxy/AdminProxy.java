package com.example.admin.proxy;

import java.util.List;

import com.example.admin.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProxy {

	private Integer id;
	private String userName;
	private String password;
	private String email;
	
	private RoleEnum role;
	
//    private List<String> imageUrl;
    
    private String profilePic;
	
}
