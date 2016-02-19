
var app = angular.module("MyApp");
app.controller("companyEmployeeCtrl", ['$scope', '$state', '$stateParams', '$modal', '$log', 'Company', function ($scope, $state, $stateParams, $modal, $log, Company) {

    var id = $stateParams.id;

    $scope.companyEmployees = function (id) {
        return Company.companyEmployees(id)
        .then(function (data) {
            $scope.employees = Company.employees;
        });
    };

    $scope.companyEmployees(id);
}]);