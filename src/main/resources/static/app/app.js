
var app = angular.module('MyApp', [
    'ui.router',
    'ui.bootstrap',
    'ngAnimate',
    //'angularFileUpload'
]);

app.config(['$urlRouterProvider', '$stateProvider', function ($urlRouterProvider, $stateProvider) {

    // default route
    $urlRouterProvider.otherwise('/');

    $stateProvider
        /*.state('home', {
            url: '/',
            templateUrl: 'app/home/home.html',
            controller: 'homeCtrl'
        })
        .state('about', {
            url: '/about',
            templateUrl: 'app/about/about.html',
            controller: 'aboutCtrl'
        })*/
        .state('employee', {
            url: '/employee',
            templateUrl: 'app/employee/employee.html',
            controller: 'employeeCtrl'
        }) 
        .state('company', {
            url: '/',
            templateUrl: 'app/company/company.html',
            controller: 'companyCtrl'
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

app.directive('loading', ['$http', function ($http) {
    return {
        restrict: 'A',
        link: function (scope, elm, attrs) {
            scope.isLoading = function () {
                return $http.pendingRequests.length > 0;
            };
            scope.$watch(scope.isLoading, function (v) {
                if (v) {
                    elm.show();
                } else {
                    elm.hide();
                }
            });
        }
    };

}]);

app.directive('fileUpload', function () {
    return {
        scope: true,        //create a new scope
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var files = event.target.files;
                //iterate files since 'multiple' may be specified on the element
                for (var i = 0; i < files.length; i++) {
                    //emit event upward
                    scope.$emit("fileSelected", { file: files[i] });
                }
            });
        }
    };
});

