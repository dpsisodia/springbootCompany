package com.assignmt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignmt.model.Company;
import com.assignmt.repository.CompanyRepository;
import com.assignmt.repository.EmployeeRepository;



@Service
@Transactional(readOnly = true)
public class CompanyService {
	public static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
    private CompanyRepository companyRepo;
    
    @Autowired
    private EmployeeRepository empRepo;

    public List<Company> searchCompanies(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();
        return  companyRepo.searchCompanies(keyword, new PageRequest(page, pageSize));
    }
    public Company getCompanyDetails(Integer id) {
        return companyRepo.findOne(id);
    }

    @Transactional
    public Company saveCompany(Company company) {
    	logger.trace("inside saveCompany. company={}", company);
    	if(company.getId() != null) {
    		Company compFromDB = companyRepo.getOne(company.getId());
    		logger.debug("companyRepo.getOne()={}",compFromDB);
    		compFromDB.setAddress(company.getAddress());
    		compFromDB.setName(company.getName());
    		compFromDB.setCity(company.getCity());
    		compFromDB.setCountry(company.getCountry());
    		compFromDB.setEmail(company.getEmail());
    		compFromDB.setPhoneNumber(company.getPhoneNumber());
    		return companyRepo.saveAndFlush(compFromDB);

    	} else { 
    		// if company does not exists then create new company and its associates 
    		throwExceptionIfNameAlreadyExist(company);
    		return companyRepo.saveAndFlush(company);
    	}
    }

	@Transactional
    public void deleteCompanys(Integer... ids) {
    	for(Integer id:ids) {
    		Integer deletedEmployees = empRepo.deleteEmployeesByCompanyId(id);
    		logger.info("Employees deleted count={}, companyId={}", deletedEmployees, id);
    		empRepo.flush();
    		
    		Company company = companyRepo.getOne(id);
    		companyRepo.delete(company);
    		companyRepo.flush();
    	}
    }
    
    @Transactional
    public void deleteAllCompanys() {
        companyRepo.deleteAllInBatch();
    }
    
    private void throwExceptionIfNameAlreadyExist(Company company) {
    	if (companyRepo.findByName(company.getName()) != null) { 
    		throw new IllegalArgumentException(
    				"'"+company.getName()+"' name is already used by other company");
    	}
	}

}