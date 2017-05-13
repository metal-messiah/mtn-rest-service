(function () {
    'use strict';

    angular.module('mtn').controller('LoginController', LoginController);

    function LoginController($scope, $location, $timeout, $routeParams, Auth, Cache) {
        var vm = this;

        init();

        ////////////////////////

        function init() {
            //Allow a small timeout before popping the login
            if (Auth.isAuthenticated() || $routeParams['sign-in-success']) {
                $location.path('/');
                $location.search('sign-in-success', null);
                return;
            } else {
                $scope.timeout = $timeout(function () {
                    Cache.clear();
                    Auth.login();
                }, 500);
            }
        }

        $scope.$on('$destroy', function () {
            if ($scope.timeout) {
                $timeout.cancel($scope.timeout);
            }
        });
    }
})();