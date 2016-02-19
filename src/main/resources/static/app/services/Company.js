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
//        	   alert(param);
               var deferred = $q.defer();
               return $http.post(baseUrl , param)
                .success(function (data) {
                    deferred.resolve(companyService.company = data);
                })

               .error(function (error) {
                   deferred.reject(error);
               });
               return deferred.promise;
           }

           // Companys detail by company id
           companyService.companyDetail = function (id) {
               var deferred = $q.defer();
               return $http.get(baseUrl + "/" + id)
                    .success(function (data) {
                        deferred.resolve(
                            companyService.currentCompany = data);
                    })
               .error(function (error) {
                   deferred.reject(error);
               })
               return deferred.promise;
           }

           // delete Companys
           companyService.delete = function (id) {
               var deferred = $q.defer();
               return $http.delete(baseUrl + "/" + id)
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
               return $http.get(employeeBaseUrl + "Byid/" + id)
                    .success(function (data) {
                        deferred.resolve(companyService.employees = data);
                    }).error(function (error) {
                        deferred.reject(error);
                    })
               return deferred.promise;
           }

           return companyService;
       }]);