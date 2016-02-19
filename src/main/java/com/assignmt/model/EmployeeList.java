package com.assignmt.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;
/**
 * Encaspsulation of Employee class to so that Spring can invoke validations on each employee object.
 * @author dsisodia
 *
 */
public class EmployeeList {	

	@Size(min=1, message="min.one.length")
	List<Employee> employees = new ArrayList<Employee>();

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void add(Employee emp) {
		this.employees.add(emp);
	}
	
}
