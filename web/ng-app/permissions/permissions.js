(function () {
    'use strict';

    angular.module('mtn').factory('Permissions', Permissions);

    function Permissions($http) {
        var service = {
            findAll: findAll,
            findOne: findOne,
            updateOne: updateOne
        };

        return service;

        ////////////////////////////

        function findAll() {
            return $http
                .get('/api/permission', new DefaultParams())
                .then(function (response) {
                    return response.data.content;
                });
        }

        function findOne(id) {
            return $http
                .get('/api/permission/' + id)
                .then(function (response) {
                    return response.data;
                });
        }

        function updateOne(permission) {
            return $http
                .put('/api/permission/' + permission.id, permission)
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();