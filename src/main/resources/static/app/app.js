
var app = angular.module('MyApp', [
    'ui.router',
    'ui.bootstrap',
    'ngAnimate'
]);

app.config(['$urlRouterProvider', '$stateProvider', function ($urlRouterProvider, $stateProvider) {

    // default route
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('employee', {
            url: '/employee',
            templateUrl: 'app/employee/employee.html',
            controller: 'employeeCtrl'
        }) 
        .state('company.detail', {
            url: '^/company/detail/{id:[0-9]{1,5}}',
            templateUrl: 'app/company/companydetail.html',
            controller: 'companyCtrl'
        })
        .state('company.detail.employee', {
            url: '^/company/detail/employee/{id:[0-9]{1,5}}',
            templateUrl: 'app/company/employee.html',
            controller: 'companyEmployeeCtrl'
        })
}]);


