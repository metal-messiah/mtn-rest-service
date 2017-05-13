(function () {
    'use strict';

    angular.module('mtn').factory('Groups', Groups);

    function Groups($http, $log, $q, Toaster) {
        var service = {
            addOne: addOne,
            deleteOne: deleteOne,
            findAll: findAll,
            findOne: findOne,
            updateOne: updateOne
        };

        return service;

        ////////////////////////////

        function addOne(group) {
            return $http
                .post('/api/group', group)
                .then(function (response) {
                    $log.info('Successfully added group', response.data);
                    Toaster.success('Successfully added the Group');
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add group', response);
                    Toaster.error('Something went wrong trying to add the Group');
                    return $q.reject(response);
                });
        }

        function deleteOne(id) {
            return $http
                ['delete']('/api/group/' + id)
                .then(function () {
                    $log.info('Successfully deleted the group');
                    Toaster.success('Successfully deleted Group');
                })
                .catch(function (response) {
                    $log.error('Failed to delete group', response);
                    Toaster.error('Something went wrong trying to delete the Group');
                    return $q.reject(response);
                });
        }

        function findAll() {
            return $http
                .get('/api/group', new DefaultParams())
                .then(function (response) {
                    $log.info('Successfully retrieved groups', response.data.content);
                    return response.data.content;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve groups', response);
                    Toaster.error('Something went wrong trying to load the Groups');
                    return $q.reject(response);
                });
        }

        function findOne(id) {
            return $http
                .get('/api/group/' + id)
                .then(function (response) {
                    $log.info('Successfully retrieved group', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve group', response);
                    Toaster.error('Something went wrong trying to load the Group');
                    return $q.reject(response);
                });
        }

        function updateOne(group) {
            return $http
                .put('/api/group/' + group.id, group)
                .then(function (response) {
                    $log.info('Successfully updated the Group', response.data);
                    Toaster.success('Successfully updated Group');
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update group', response);
                    Toaster.error('Something went wrong trying to update the Group');
                    return $q.reject(response);
                });
        }
    }
})();