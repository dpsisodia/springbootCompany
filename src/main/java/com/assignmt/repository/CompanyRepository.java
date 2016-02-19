package com.assignmt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignmt.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	  @Query("select c from Company c where  "
	            + " lower(c.name) like :keyword% "
	            + "or lower(c.address) like :keyword% "
	            + "or lower(c.city) like :keyword% "
	            + "or lower(c.email) like :keyword% "
	            + "or c.phoneNumber like :keyword% "
	            + "or lower(c.country) like :keyword% "
	            + "order by c.name")
	  List<Company> searchCompanies(@Param("keyword") String keyword,  Pageable pageable);
	    
	  @Query("select c from Company c where name=:name")
	  Company findByName(@Param("name") String name);
	

}
