angular.module('MyApp')
       .factory('Company', ['$q', '$http', function ($q, $http) {

           var baseUrl = 'api/companies/'; //'api/company/';
           var employeeBaseUrl = 'api/employees/'; //'api/Employee/';

           var companyService = {};
           companyService.companies = [];
           companyService.currentCompany = {};

           // Search Companys
           companyService.search = function (text) {
               var deferred = $q.defer();
               return $http({
                   url: baseUrl,
                   method: 'GET',
                   params: { 'keyword': text },
                   cache: true
               }).success(function (data) {
                   deferred.resolve(
                       companyService.companies = data);
               }).error(function (error) {
                   deferred.reject(error);
               })
               return deferred.promise;
           }

           // New Companys
           companyService.newCompany = function () {
               var deferred = $q.defer();
               return $http.get(baseUrl)
                    .success(function (data) {
                        deferred.resolve(companyService.company = data);
                    })
               .error(function (error) {
                   deferred.reject(error);
               })
               return deferred.promise;
           }

           // Save Company
           companyService.Save = function (company, files) {
        	   var param = JSON.stringify({"id":company.id,
        		   "name":company.name,
        		   "address":company.address,
        		   "city":company.city,
        			"country":company.country});
        	   
        	   
        	   return postCompany(baseUrl , param, company)
        	   				.then(postEmployees)
        	   				.then(function(result){
        	   					//alert('final:'+JSON.stringify(result));
        	   					var deferred = $q.defer();
        	   					deferred.resolve(companyService.company);
        	   					return deferred.promise;
        	   				});
        	   
           };
           function postCompany(baseUrl , param, company){
        	   var deferred = $q.defer();
        	   return $http.post(baseUrl , param)
             .success(function (data) {
                 deferred.resolve(companyService.company = data,
                		 companyService.company.employees = [],
                		 companyService.company.employees = company.employees
                		 
                		 );
             })

            .error(function (error) {
                deferred.reject(error);
            });

        	   return deferred.promise;
           }
           function postEmployees(result){
        	   var deferred = $q.defer();
        	   companyService.company = result.data;
             for(i=0;i<companyService.company.employees.length;i++){
            	 companyService.company.employees[i].companyId=companyService.company.id
             }
             var empReq = angular.toJson({"employees":companyService.company.employees});
             //alert(empReq)
        	   return $http.post(employeeBaseUrl , empReq)
             .success(function (data) {
             	companyService.company.employees =[];
             	companyService.company.employees = data.employees;
                 deferred.resolve(companyService.company);
             })

            .error(function (error) {
                deferred.reject(error);
            });

        	   return deferred.promise;
           }
           // Companys detail by company id
           companyService.companyDetail = function (id) {
        	   var deferred = $q.defer();
               var urls = [baseUrl + "/" + id, employeeBaseUrl + "?companyId=" + id];
        	   var urlCalls = [];
               //alert(JSON.stringify(urls))
               angular.forEach(urls, function(url) {
                 urlCalls.push($http.get(url));
               });
               // they may, in fact, all be done, but this
               // executes the callbacks in then, once they are
               // completely finished.
               $q.all(urlCalls)
               .then(
                 function(results) {
                	 //alert(JSON.stringify(results));
                	 deferred.resolve(
                  
                  companyService.currentCompany = results[0].data,
                  companyService.currentCompany.employees = results[1].data
                  )
               },
               function(errors) {
                 deferred.reject(errors);
               },
               function(updates) {
                 deferred.update(updates);
               });
               return deferred.promise;
             }
           


           // delete Companys
           companyService.delete = function (url) {
               var deferred = $q.defer();
               return $http.delete(url)
                    .success(function (data) {
                        deferred.resolve();
                    })
               .error(function (error) {
                   deferred.reject(error);
               })
               return deferred.promise;
           }
                      
           /*       COMPANY EMPLOYEES
            ************************************/
           companyService.companyEmployees = function (id) {
               var deferred = $q.defer();
               return $http.get(employeeBaseUrl + "?companyId=" + id)
                    .success(function (data) {
                        deferred.resolve(companyService.employees = data);
                    }).error(function (error) {
                        deferred.reject(error);
                    })
               return deferred.promise;
           }

           return companyService;
       }]);