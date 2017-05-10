(function () {
    'use strict';

    angular.module('mtn').factory('Users', Users);

    function Users($http, $log, $q) {
        var service = {
            addOne: addOne,
            deleteOne: deleteOne,
            findAll: findAll,
            findOne: findOne,
            updateOne: updateOne
        };

        return service;

        ////////////////////////////

        function addOne(user) {
            return $http
                .post('/api/user', user)
                .then(function (response) {
                    $log.info('Successfully added user', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add user', response);
                    return $q.reject(response);
                });
        }

        function deleteOne(id) {
            return $http
                ['delete']('/api/user/' + id)
                .then(function () {
                    $log.info('Successfully deleted user');
                })
                .catch(function (response) {
                    $log.error('Failed to delete user', response);
                    return $q.reject(response);
                });
        }

        function findAll(q) {
            var config = new DefaultParams();

            if (q) {
                config.add('q', q);
            }

            return $http
                .get('/api/user', config)
                .then(function (response) {
                    for (var i = 0; i < response.data.content.length; i++) {
                        postProcess(response.data.content[i]);
                    }

                    $log.info('Successfully retrieved users', response.data.content);
                    return response.data.content;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve users', response);
                    return $q.reject(response);
                });
        }

        function findOne(id) {
            return $http
                .get('/api/user/' + id)
                .then(function (response) {
                    $log.info('Successfully retrieved user', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve user', response);
                    return $q.reject(response);
                });
        }

        function postProcess(user) {
            if (user.firstName && user.lastName) {
                user.displayName = user.firstName + ' ' + user.lastName;
            }
        }

        function updateOne(user) {
            return $http
                .put('/api/user/' + user.id, user)
                .then(function (response) {
                    $log.info('Successfully updated user', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update user', response);
                    return $q.reject(response);
                });
        }
    }
})();