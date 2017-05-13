(function () {
    'use strict';

    angular.module('mtn').controller('IndexController', IndexController);

    function IndexController(Auth) {
        var vm = this;

        vm.isAuthenticated = Auth.isAuthenticated;
    }
})();