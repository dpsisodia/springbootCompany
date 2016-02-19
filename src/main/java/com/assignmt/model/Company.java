package com.assignmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
public class Company {
  
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
  
	@Column(unique=true)
	@NotBlank(message = "validation.name.not-empty.message")
	private String name;
  
	@Column
	@NotBlank(message = "validation.add.not-empty.message")
	private String address;
	
	@Column
	@NotBlank(message = "validation.city.not-empty.message")
	private String city;
	  
	@Column
	@NotBlank(message = "validation.country.not-empty.message")
	private String country;
	
	@Column(nullable=true)
	@Email(message = "validation.company.email.message")
	private String email;
	
	@Column(nullable=true)
	@NumberFormat(style = Style.NUMBER)
	private Long phoneNumber;
	
	@Version
	private Integer version = 0;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer companyId) {
		this.id = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public Set<Employee> getEmployees() {
//		return employees;
//	}
//
//	public void setEmployees(Set<Employee> employees) {
//		this.employees = employees;
//	}

	@Override
	public String toString() {
		String result = "Company [id=" + id + ", name=" + name + "]";
//		for(Employee e :employees) {
//			result+=e.toString();
//		}
		return result;
	}

}
