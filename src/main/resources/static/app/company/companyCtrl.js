﻿var app = angular.module("MyApp")
app.controller('companyCtrl', ['$scope', '$state', '$stateParams', '$modal', '$log', 'Company', function ($scope, $state, $stateParams, $modal, $log, Company) {

    var id = $stateParams.id;

    $scope.searchText = '';
    $scope.companies = searchCompanys();
    $scope.employees = [];
    $scope.company = null;
    $scope.currentCompany = null;


    $scope.$watch('searchText', function (newVal, oldVal) {
        if (newVal != oldVal) {
            searchCompanys();
        }
    }, true);


    function searchCompanys() {
        Company.search($scope.searchText)
        .then(function (data) {
            $scope.companies = Company.companies;
        });
    };

    $scope.companyDetail = function (id) {
        if (!id) return;
        Company.companyDetail(id)
        .then(function (data) {
            $scope.currentCompany = Company.currentCompany;
            $state.go('company.detail', { 'id': id });
        });
    };

    /* Need to call after defining the function
       It will be called on page refresh        */
    $scope.currentCompany = $scope.companyDetail(id);

    // Delete a company and hide the row
    $scope.deleteCompany = function ($event, id) {
        var ans = confirm('Are you sure to delete it?');
        if (ans) {
            Company.delete(id)
            .then(function () {
                var element = $event.currentTarget;
                $(element).closest('div[class^="col-lg-12"]').hide();
            })
        }
    };

    // Add Company
    $scope.addCompany = function () {
        Company.newCompany()
        .then(function (data) {
            $scope.company = Company.company;
            $scope.open('lg');
        });
    }

    // Edit Company
    $scope.editCompany = function () {
        $scope.company = $scope.currentCompany;
        $scope.open('lg');
    }

    // Open model to add edit company
    $scope.open = function (size) {        
        var modalInstance = $modal.open({
            animation: false,
            backdrop: 'static',
            templateUrl: 'app/company/AddEditCompany.html',
            controller: 'companyModalCtrl',
            size: size,
            resolve: {
                company: function () {
                    return $scope.company;
                }
            }
        });
        modalInstance.result.then(function (response) {
            $scope.currentCompany = response;
            $state.go('company.detail', { 'id': response.id });            
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    
}]);

app.controller('companyModalCtrl', ['$scope', '$modalInstance', 'Company', 'company', function ($scope, $modalInstance, Company, company) {

    $scope.company = company;
    
    if (company.id > 0)
        $scope.headerTitle = 'Edit Company';
    else
        $scope.headerTitle = 'Add Company';
    
    $scope.save = function () {
        Company.Save($scope.company).then(function (response) {
            $modalInstance.close(response.data);
        })
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.company.employees = [{name:'',email:'',type:'Employee'}];
    

                   $scope.addPerson = function(){
                     var person = {
                         name: '',
                         email:'',
                         type: 'Employee'//default
                        	 
                     };

                     $scope.company.employees.push(person);
                   }; 

                   $scope.removePerson = function(index){
                     $scope.company.employees.splice(index, 1);
                   };  
    
}]);
