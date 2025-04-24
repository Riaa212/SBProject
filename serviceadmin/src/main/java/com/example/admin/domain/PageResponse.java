package com.example.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

	private Integer pageNumber;
	
	private Integer pageSize;
	
	private String userName;
}
