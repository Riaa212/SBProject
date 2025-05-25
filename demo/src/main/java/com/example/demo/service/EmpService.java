package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.domain.Employee;

@Component
public class EmpService {
	
	private static List<Employee> emplst =new ArrayList<>();
	//get all & add employee
	public static List<Employee> show()
	{
		emplst.add(new Employee(101,"Ria",780000.00,"AHD","FeMale"));
		emplst.add(new Employee(102,"John",560000.00,"RJ","Male"));
		emplst.add(new Employee(103,"Niya",90000.00,"HR","FeMale"));
		emplst.add(new Employee(104,"Sahil",70000.00,"AHD","Male"));
		return emplst;
	}
	

	//grouping based on Gender
	public Map<String,List<String>> Grouping()
	{
		show();
		Map<String, List<String>> collect = emplst.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.mapping(Employee::getName, Collectors.toList())));
		return collect;
	}
	
	
	
	
	
}
