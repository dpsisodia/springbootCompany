
var app = angular.module('MyApp');

app.directive("asCompany", function () {
    return {
        restrict: 'E',
        replace: 'true',
        templateUrl: 'app/company/userListRow.html'
    }
});