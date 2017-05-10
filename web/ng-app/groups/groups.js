(function () {
    'use strict';

    angular.module('mtn').factory('Groups', Groups);

    function Groups($http, $log, $q) {
        var service = {
            addOne: addOne,
            addOneMemberToGroup: addOneMemberToGroup,
            deleteOne: deleteOne,
            findAll: findAll,
            findAllMembersForGroup: findAllMembersForGroup,
            findOne: findOne,
            removeOneMemberFromGroup: removeOneMemberFromGroup,
            updateOne: updateOne
        };

        return service;

        ////////////////////////////

        function addOne(group) {
            return $http
                .post('/api/group', group)
                .then(function (response) {
                    $log.info('Successfully added group', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add group', response);
                    return $q.reject(response);
                });
        }

        function addOneMemberToGroup(groupId, userId) {
            return $http
                .post('/api/group/' + groupId + '/member/' + userId)
                .then(function (response) {
                    $log.info('Successfully added member to group', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add member to group', response);
                    return $q.reject(response);
                });
        }

        function deleteOne(id) {
            return $http
                ['delete']('/api/group/' + id)
                .then(function () {
                    $log.info('Successfully deleted group');
                })
                .catch(function (response) {
                    $log.error('Failed to delete group', response);
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
                    return $q.reject(response);
                });
        }

        function findAllMembersForGroup(id) {
            return $http
                .get('/api/group/' + id + '/member')
                .then(function (response) {
                    $log.info('Successfully retrieved group members', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve group members', response);
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
                    return $q.reject(response);
                });
        }

        function removeOneMemberFromGroup(groupId, userId) {
            return $http
                ['delete']('/api/group/' + groupId + '/member/' + userId)
                .then(function (response) {
                    $log.info('Successfully removed member from group', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to remove member from group', response);
                    return $q.reject(response);
                });
        }

        function updateOne(group) {
            return $http
                .put('/api/group/' + group.id, group)
                .then(function (response) {
                    $log.info('Successfully updated group', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update group', response);
                    return $q.reject(response);
                });
        }
    }
})();