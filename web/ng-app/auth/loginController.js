(function () {
    'use strict';

    angular.module('mtn').controller('LoginController', LoginController);

    function LoginController($location, Auth) {
        var vm = this;

        init();

        ////////////////////////

        //TODO during init(), check if currently authenticated. If so, redirect to dashboard. If not, clear all cached authentication.

        function init() {
            if (Auth.isAuthenticated()) {
                $location.path('/');
                return;
            } else {
                Auth.login();
            }
        }
    }
})();