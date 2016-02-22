#Company Management Tool 
=======================
	
Company Management is a tool that manages complete lifecycle of a company, its employees and owners.  
* Main Technology Stack:
1. Spring Boot 1.2.5, Spring Data JPA
2. Java 8,
3. Bean Validations
4. Angular 1.3
5. Docker-Heroku 


cURL help
---------
First a Company is required to be created before creating and assigning employees/owners to it. Therefore follow the sequence below:
curld JSON data request files for different operation is present in current directory. And traverse to this directory before issuing curl commands.
###POST Company:
$ curl -X POST -d @postCompany.txt http://localhost:8080/api/companies --header "Content-Type:application/json"
{"id":1,"name":"na","address":"Dighi","city":"Pune","country":"India","email":null,"phoneNumber":9004126639}

###POST Employees:
$ curl -X POST -d @postEmp.txt http://localhost:8080/api/employees --header "Content-Type:application/json"
{"employees":[{"id":1,"name":"Some Owner Name1","type":"Employer","companyId":1,"email":"dev@dev.com"},{"id":2,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev1@dev.com"}]}

###PUT Company:
$ curl -X PUT -d @putCompany.txt http://localhost:8080/api/companies/1 --header "Content-Type:application/json"
{"id":1,"name":"na1","address":"Dighi","city":"Pune","country":"India","email":null,"phoneNumber":9004126639}

###PUT Employees:
$ curl -X PUT -d @putEmp.txt http://localhost:8080/api/employees --header "Content-Type:application/json"
{"employees":[{"id":1,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev@dev.com"},{"id":2,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev1@dev.com"}]}

###GET Company:
$ curl -X GET  http://localhost:8080/api/companies?keyword=T&page=1&pageSize=10
response:[1] 2100
		[2] 4340
		[2]+  Done                    page=1
$ [{"id":1,"name":"Tata Communications","address":"A1.","city":"Pune","country":"INdia","email":null,"phoneNumber":null}]

###GET Employees(flavour 1 -byCompanyId):
$ curl -X GET http://localhost:8080/api/employees?companyId=1&page=0&pageSize=10
[{"id":1,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev@dev.com"},{"id":2,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev1@dev.com"}]

###GET Employees(flavour 2 -all):
$ curl -X GET http://localhost:8080/api/employees?page=0&pageSize=10
[{"id":1,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev@dev.com"},{"id":2,"name":"Some Owner Name2","type":"Employer","companyId":1,"email":"dev1@dev.com"}]

