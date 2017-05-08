(function () {
    'use strict';

    angular.module('mtn').factory('Toaster', Toaster);

    function Toaster($mdToast) {
        var service = {
            toast: toast
        };

        return service;

        //////////////////////////

        function toast(message) {
            return $mdToast
                .show(
                    $mdToast
                        .simple()
                        .textContent(message)
                        .position('bottom right')
                        .hideDelay(3000)
                );
        }
    }
})();