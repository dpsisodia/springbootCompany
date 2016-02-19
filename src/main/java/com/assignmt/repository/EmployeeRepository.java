package com.assignmt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignmt.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	 @Query("select e from Employee e where  "
	            + "e.companyId=:companyId "
	            + "order by e.name")
	  List<Employee> getEmployeesByCompanyId(@Param("companyId") Integer companyId,  Pageable pageable);
	 
	 @Query("select e from Employee e order by e.name")
	  List<Employee> getAllEmployees(Pageable pageable);
	
	 @Modifying
	 @Query("delete from Employee e where e.companyId=:companyId")
	 Integer deleteEmployeesByCompanyId(@Param("companyId") Integer companyId);
	 
	  @Query("select e from Employee e where email=:email")
	  Employee findByEmail(@Param("email") String email);

	

}
