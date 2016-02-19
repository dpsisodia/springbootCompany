package com.assignmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

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

import com.assignmt.model.Company;
import com.assignmt.service.CompanyService;
import com.assignmt.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
  @Autowired
  private CompanyService companyService;
  
  @Autowired
  private EmployeeService employeeService;
  
  @RequestMapping(method = GET)
  public List<Company> searchContacts(
          @RequestParam(defaultValue="") String keyword, 
          @RequestParam(defaultValue="0") int page, 
          @RequestParam(defaultValue="200") int pageSize) {
      return companyService.searchCompanies(keyword, page, pageSize);
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Company findCompany(@PathVariable Integer id,
		  @RequestParam(defaultValue="0") int page, 
          @RequestParam(defaultValue="200") int pageSize) {
	  	  return companyService.getCompanyDetails(id);
  }
  
  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Company addCompany(@RequestBody @Validated Company newCompany) {
	  return companyService.saveCompany(newCompany);
  }
  

@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
@ResponseStatus(HttpStatus.ACCEPTED)
  public Company updateCompany(@RequestBody @Validated Company updatedCompany, @PathVariable Integer id) {
    updatedCompany.setId(id);
    return companyService.saveCompany(updatedCompany);
  }

@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteCompany(@PathVariable Integer[] id) {
  companyService.deleteCompanys(id);
}

}
