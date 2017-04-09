angular.module('mtn').controller('IndexCtrl', IndexCtrl);

function IndexCtrl($scope, AuthService) {
    var vm = this;

    $scope.$on('$destroy', AuthService.logout);
}