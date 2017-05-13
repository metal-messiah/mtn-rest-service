(function () {
    'use strict';

    angular.module('mtn').factory('Roles', Roles);

    function Roles($http, $log, $q, Toaster) {
        var service = {
            addOne: addOne,
            deleteOne: deleteOne,
            findAll: findAll,
            findOne: findOne,
            updateOne: updateOne
        };

        return service;

        ////////////////////////////

        function addOne(role) {
            return $http
                .post('/api/role', role)
                .then(function (response) {
                    $log.info('Successfully added role', response.data);
                    Toaster.success('Successfully added the Role');
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add role', response);
                    Toaster.error('Something went wrong adding the Role');
                    return $q.reject(response);
                });
        }

        function deleteOne(id) {
            return $http
                ['delete']('/api/role/' + id)
                .then(function () {
                    $log.info('Successfully deleted role');
                    Toaster.success('Successfully deleted the Role');
                })
                .catch(function (response) {
                    $log.error('Failed to delete role', response);
                    Toaster.error('Something went wrong deleting the Role');
                    return $q.reject(response);
                });
        }

        function findAll() {
            return $http
                .get('/api/role', new DefaultParams())
                .then(function (response) {
                    $log.info('Successfully retrieved roles', response.data.content);
                    return response.data.content;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve roles', response);
                    Toaster.error('Something went wrong loading the Roles');
                    return $q.reject(response);
                });
        }

        function findOne(id) {
            return $http
                .get('/api/role/' + id)
                .then(function (response) {
                    $log.info('Successfully retrieved role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve role', response);
                    Toaster.error('Something went wrong loading the Role');
                    return $q.reject(response);
                });
        }

        function updateOne(role) {
            return $http
                .put('/api/role/' + role.id, role)
                .then(function (response) {
                    $log.info('Successfully updated role', response.data);
                    Toaster.success('Successfully updated the Role');
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update role', response);
                    Toaster.error('Something went wrong updating the Role');
                    return $q.reject(response);
                });
        }
    }
})();