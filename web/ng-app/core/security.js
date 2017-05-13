(function () {
    'use strict';

    angular.module('mtn').factory('Security', Security);

    function Security(Cache) {
        var service = {
            check: check
        };

        return service;

        /////////////////////////

        function check(permission) {
            return Cache.user() && Cache.user().permissions.indexOf(permission) !== -1;
        }
    }
})();