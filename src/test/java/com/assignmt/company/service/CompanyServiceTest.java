package com.assignmt.company.service;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assignmt.Application;
import com.assignmt.model.Company;
import com.assignmt.model.Employee;
import com.assignmt.model.EmployeeList;
import com.assignmt.service.CompanyService;
import com.assignmt.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CompanyServiceTest {
    private static final String SOME_NAME = "some Name";
	
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService empService;
	private Integer companyId;
	private Company company;
    
	@Before
	@Transactional
    public void startUp() throws IOException {
		System.out.println("inside startUp ...");
        company = companyService.saveCompany(createNewCompany(1));
        assertNotNull(company);
        companyId = company.getId();
        System.out.println("exiting startUp ...");
    }
    
    private Company createNewCompany(int nth) {
    	Company c = new Company();
    	c.setName(nth+SOME_NAME);
    	c.setAddress(nth+"some Address");
    	c.setCity(nth+"some city");
    	c.setCountry(nth+"India");
    	c.setPhoneNumber(nth+9004126639l);
    	c.setEmail(nth+"someone@somewhere.com");
    	c = companyService.saveCompany(c);
    	
    	Employee e1 = createEmp(nth+"empNameOwner","Owner", nth+"@1.com", c);
    	Employee e2 = createEmp(nth+"empNameEmployee","Employee", nth+"@2.com", c);
    	List<Employee> employees = new ArrayList<Employee>();
    	employees .add(e1);
    	employees .add(e2);
    	EmployeeList list = new EmployeeList();
    	list.add(e1);list.add(e2);
    	empService.saveEmployee(list);
		return c;
	}

	private Employee createEmp(String empName, String type, String email, Company c) {
		Employee e = new Employee();
		e.setName(empName);
		e.setType(type);
		e.setEmail(email);
		e.setCompanyId(c.getId());
		return e;
	}

	@After
	@Transactional
    public void tearDown() {
		List<Company> list = companyService.searchCompanies("", 1, 10);
		System.out.println("inside tearDown ..."+list.size());
		for(Company comp:list) {
			System.out.println("**************tearDown CompanyId="+comp.getId());
			companyService.deleteCompanys(comp.getId());
		}
//        companyService.deleteAllCompanys();
		System.out.println("exiting tearDown ...");
    }
 
	@Test
	@Transactional
	public void testGetDetails() {
	        Company c = companyService.getCompanyDetails(companyId);
	        assertNotNull(c);
	        List<Employee> l = empService.searchEmployeesByCompanyId(companyId, 0, 200);
	        assertThat(l.size(), is(equalTo(2)));
	}
	
    @Test
    @Transactional
    public void testSearchCompany() {
    	Company comp2 = companyService.saveCompany(createNewCompany(2));
    	Company comp3 = companyService.saveCompany(createNewCompany(3));
    	 
        List<Company> companies;
        
        // search all
        companies = companyService.searchCompanies("", 0, 10);
        assertThat(companies.size(), is(equalTo(3)));

        // search by name
        companies = companyService.searchCompanies("2", 0, 10);
        assertThat(companies.size(), is(equalTo(1)));
        assertThat(companies.get(0).getId(), is(equalTo(comp2.getId())));
        

        companies = companyService.searchCompanies("3", 0, 10);
        assertThat(companies.size(), is(equalTo(1)));
        assertThat(companies.get(0).getId(), is(equalTo(comp3.getId())));
    }
    
    @Test
    @Transactional
    public void testSaveCompany() {
    	// create
        Company comp2 = createNewCompany(2);
        comp2 = companyService.saveCompany(comp2);
        assertThat(comp2.getName(), is(equalTo(2+SOME_NAME)));
        
        List<Company> companies = companyService.searchCompanies("", 0, 10);
        assertThat(companies.size(), is(equalTo(2)));
//        Company company = companies.get(0);
//        assertThat(companies.size(), is(equalTo(2)));
        
        // update
        company = companyService.getCompanyDetails(company.getId());
        assertNotNull(company);
        company.setName("update full name");
        companyService.saveCompany(company);
        company = companyService.getCompanyDetails(company.getId());
        assertThat(company.getName(), is(equalTo("update full name")));
    }
    
    @Test
    @Transactional
    public void testDeleteContact() {
        List<Company> companies;
        companyService.saveCompany(createNewCompany(2));
        companies = companyService.searchCompanies("", 0, 10);
        assertThat(companies.size(), is(equalTo(2)));
        
        companyService.deleteCompanys(1);
        
        companies = companyService.searchCompanies("", 0, 10);
        assertThat(companies.size(), is(equalTo(1)));
        
    }
}
