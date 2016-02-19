package com.assignmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignmt.model.Employee;
import com.assignmt.model.EmployeeList;
import com.assignmt.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(method = GET)
	public List<Employee> searchContacts(@RequestParam(defaultValue = "") Integer companyId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "200") int pageSize) {
		return employeeService.searchEmployeesByCompanyId(companyId, page, pageSize);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Employee findEmployee(@PathVariable Integer id) {
		return employeeService.getEmployeeDetails(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeList addEmployee(@RequestBody @Validated EmployeeList newEmployees) {
		return employeeService.saveEmployee(newEmployees);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public EmployeeList updateEmployee(@RequestBody @Validated EmployeeList updatedEmployees) {
		// updatedEmployee.setId(id);
		return employeeService.saveEmployee(updatedEmployees);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable Integer[] id) {
		employeeService.deleteEmployees(id);
	}

}
