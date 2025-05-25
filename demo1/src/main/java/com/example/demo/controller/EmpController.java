package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Employee;
import com.example.demo.service.EmpService;

@RestController
@RequestMapping("/demo1")
public class EmpController {

	
	private EmpService empservice;
	
	@GetMapping("/emp")
	public List<Employee> getAll()
	{
		return empservice.show();
	}
}
