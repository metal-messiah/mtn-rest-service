(function () {
    'use strict';

    angular.module('mtn').factory('Security', Security);

    function Security(Cache) {
        var service = {
            check: check,
            checkAny: checkAny
        };

        return service;

        /////////////////////////

        function check(permission) {
            return Cache.user() && Cache.user().permissions.indexOf(permission) !== -1;
        }

        function checkAny(permissions) {
            for (var i = 0; i < permissions.length; i++) {
                if (service.check(permissions[i])) {
                    return true;
                }
            }
            return false;
        }
    }
})();