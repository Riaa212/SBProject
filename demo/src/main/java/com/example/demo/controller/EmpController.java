package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Employee;
import com.example.demo.service.EmpService;

@RestController
public class EmpController {

	private EmpService emp;

	public EmpController(EmpService emp) {
		super();
		this.emp = emp;
	}
	
	@GetMapping("/getData")
	public List<Employee> show()
	{
		return emp.show();
	}
	
	@GetMapping("/grouping")
	public Map<String,List<String>> groping()
	{
		return emp.Grouping();
	}
}

