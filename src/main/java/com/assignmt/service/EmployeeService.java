package com.assignmt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignmt.model.Company;
import com.assignmt.model.Employee;
import com.assignmt.model.EmployeeList;
import com.assignmt.repository.CompanyRepository;
import com.assignmt.repository.EmployeeRepository;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
	public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
    private CompanyRepository companyRepo;
    
    @Autowired
    private EmployeeRepository empRepo;
   
    public List<Employee> searchEmployeesByCompanyId(Integer companyId, int page, int pageSize) {
        if(companyId != null) {
        	return  empRepo.getEmployeesByCompanyId(companyId, new PageRequest(page, pageSize));
        } else {
        	return  empRepo.getAllEmployees(new PageRequest(page, pageSize));
        }
    }
    
    public Employee getEmployeeDetails(Integer id) {
        return empRepo.findOne(id);
    }

    @Transactional
    public EmployeeList saveEmployee(EmployeeList empList) {
    	EmployeeList result = new EmployeeList();
    	
    	logger.trace("inside saveEmployee. employee.size={}", empList.getEmployees().size());
    	
    	for(Employee employee:empList.getEmployees()) {
    		
    		throwExceptionIfCompanyDonotExist(employee.getCompanyId());
    		throwExceptionIfEmailAlreadyExist(employee);
    		
    		if(employee.getId() != null) {
    			
	    		Employee empFromDB = empRepo.getOne(employee.getId());
	    		empFromDB.setName(employee.getName());
	    		empFromDB.setType(employee.getType());
	    		empFromDB.setEmail(employee.getEmail());
	    		empFromDB.setCompanyId(employee.getCompanyId());
	    		result.add(empRepo.saveAndFlush(empFromDB));
	
	    	} else {

	    		result.add(empRepo.saveAndFlush(employee));
	    	}
    	}
    	return result;
    }

	@Transactional
    public void deleteEmployees(Integer... ids) {
    	for(Integer id:ids) {
    		Employee emp = empRepo.getOne(id);
    		empRepo.delete(emp);
    		empRepo.flush();
    	}
    }
    
    @Transactional
    public void deleteAllEmployees() {
        companyRepo.deleteAllInBatch();
    }
    
    private void throwExceptionIfEmailAlreadyExist(Employee emp) {
    	if (empRepo.findByEmail(emp.getEmail()) != null 
    			&& empRepo.findByEmail(emp.getEmail()).getId() != emp.getId())  { 
    		throw new IllegalArgumentException(
    				"'"+emp.getEmail()+"' is already assigned to other employee");
    	}
	}

	private void throwExceptionIfCompanyDonotExist(Integer companyId) {
		Company companyFromDB = companyRepo.findOne(companyId);
		if(companyFromDB == null) { 
			throw new IllegalArgumentException("Company do not exist with id="+companyId);
		}
	}

}