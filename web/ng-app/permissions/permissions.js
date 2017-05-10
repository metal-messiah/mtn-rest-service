(function () {
    'use strict';

    angular.module('mtn').factory('Permissions', Permissions);

    function Permissions($http, $log, $q) {
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
                    $log.info('Successfully retrieved permissions', response.data.content);
                    return response.data.content;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve permissions', response);
                    return $q.reject(response);
                });
        }

        function findOne(id) {
            return $http
                .get('/api/permission/' + id)
                .then(function (response) {
                    $log.info('Successfully retrieved permission', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve permission', response);
                    return $q.reject(response);
                });
        }

        function updateOne(permission) {
            return $http
                .put('/api/permission/' + permission.id, permission)
                .then(function (response) {
                    $log.info('Successfully updated permission', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update permissions', response);
                    return $q.reject(response);
                });
        }
    }
})();