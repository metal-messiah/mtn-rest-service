(function () {
    'use strict';

    angular.module('mtn').factory('Permissions', Permissions);

    function Permissions($http, $log, $q, Toaster) {
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
                    Toaster.toast('Something went wrong trying to load the Permissions');
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
                    Toaster.toast('Something went wrong trying to load the Permission');
                    return $q.reject(response);
                });
        }

        function updateOne(permission) {
            return $http
                .put('/api/permission/' + permission.id, permission)
                .then(function (response) {
                    $log.info('Successfully updated the Permission', response.data);
                    Toaster.toast('Successfully updated the Permission');
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update permissions', response);
                    Toaster.toast('Something went wrong trying to update the Permission');
                    return $q.reject(response);
                });
        }
    }
})();