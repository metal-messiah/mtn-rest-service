(function () {
    'use strict';

    angular.module('mtn').factory('Groups', Groups);

    function Groups($http) {
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
                    return response.data;
                });
        }

        function addOneMemberToGroup(groupId, userId) {
            return $http
                .post('/api/group/' + groupId + '/member/' + userId)
                .then(function (response) {
                    return response.data;
                });
        }

        function deleteOne(id) {
            return $http['delete']('/api/group/' + id);
        }

        function findAll() {
            return $http
                .get('/api/group', new DefaultParams())
                .then(function (response) {
                    return response.data.content;
                });
        }

        function findAllMembersForGroup(id) {
            return $http
                .get('/api/group/' + id + '/member')
                .then(function (response) {
                    return response.data;
                });
        }

        function findOne(id) {
            return $http
                .get('/api/group/' + id)
                .then(function (response) {
                    return response.data;
                });
        }

        function removeOneMemberFromGroup(groupId, userId) {
            return $http
                ['delete']('/api/group/' + groupId + '/member/' + userId)
                .then(function (response) {
                    return response.data;
                });
        }

        function updateOne(group) {
            return $http
                .put('/api/group/' + group.id, group)
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();