(function () {
    'use strict';

    angular.module('mtn').factory('Roles', Roles);

    function Roles($http) {
        var service = {
            addOne: addOne,
            addOneMemberToRole: addOneMemberToRole,
            addOnePermissionToRole: addOnePermissionToRole,
            deleteOne: deleteOne,
            findAll: findAll,
            findAllMembersForRole: findAllMembersForRole,
            findAllPermissionsForRole: findAllPermissionsForRole,
            findOne: findOne,
            removeOneMemberFromRole: removeOneMemberFromRole,
            removeOnePermissionFromRole: removeOnePermissionFromRole,
            updateOne: updateOne
        };

        return service;

        ////////////////////////////

        function addOne(role) {
            return $http
                .post('/api/role', role)
                .then(function (response) {
                    return response.data;
                });
        }

        function addOneMemberToRole(roleId, userId) {
            return $http
                .post('/api/role/' + roleId + '/member/' + userId)
                .then(function (response) {
                    return response.data;
                });
        }

        function addOnePermissionToRole(roleId, permissionId) {
            return $http
                .post('/api/role/' + roleId + '/permission/' + permissionId)
                .then(function (response) {
                    return response.data;
                });
        }

        function deleteOne(id) {
            return $http['delete']('/api/role/' + id);
        }

        function findAll() {
            return $http
                .get('/api/role', new DefaultParams())
                .then(function (response) {
                    return response.data.content;
                });
        }

        function findAllMembersForRole(id) {
            return $http
                .get('/api/role/' + id + '/member')
                .then(function (response) {
                    return response.data;
                });
        }

        function findAllPermissionsForRole(id) {
            return $http
                .get('/api/role/' + id + '/permission')
                .then(function (response) {
                    return response.data;
                });
        }

        function findOne(id) {
            return $http
                .get('/api/role/' + id)
                .then(function (response) {
                    return response.data;
                });
        }

        function removeOneMemberFromRole(roleId, userId) {
            return $http
                ['delete']('/api/role/' + roleId + '/member/' + userId)
                .then(function (response) {
                    return response.data;
                });
        }

        function removeOnePermissionFromRole(roleId, permissionId) {
            return $http
                ['delete']('/api/role/' + roleId + '/permission/' + permissionId)
                .then(function (response) {
                    return response.data;
                });
        }

        function updateOne(role) {
            return $http
                .put('/api/role/' + role.id, role)
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();