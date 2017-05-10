(function () {
    'use strict';

    angular.module('mtn').factory('Roles', Roles);

    function Roles($http, $log, $q) {
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
                    $log.info('Successfully added role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add role', response);
                    return $q.reject(response);
                });
        }

        function addOneMemberToRole(roleId, userId) {
            return $http
                .post('/api/role/' + roleId + '/member/' + userId)
                .then(function (response) {
                    $log.info('Successfully added member to role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add member to role', response);
                    return $q.reject(response);
                });
        }

        function addOnePermissionToRole(roleId, permissionId) {
            return $http
                .post('/api/role/' + roleId + '/permission/' + permissionId)
                .then(function (response) {
                    $log.info('Successfully added permission to role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to add permission to role', response);
                    return $q.reject(response);
                });
        }

        function deleteOne(id) {
            return $http
                ['delete']('/api/role/' + id)
                .then(function () {
                    $log.info('Successfully deleted role');
                })
                .catch(function (response) {
                    $log.error('Failed to delete role', response);
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
                    return $q.reject(response);
                });
        }

        function findAllMembersForRole(id) {
            return $http
                .get('/api/role/' + id + '/member')
                .then(function (response) {
                    $log.info('Successfully retrieved role members', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve role members', response);
                    return $q.reject(response);
                });
        }

        function findAllPermissionsForRole(id) {
            return $http
                .get('/api/role/' + id + '/permission')
                .then(function (response) {
                    $log.info('Succesfully retrieved role permissions', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve role permissions', response);
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
                    return $q.reject(response);
                });
        }

        function removeOneMemberFromRole(roleId, userId) {
            return $http
                ['delete']('/api/role/' + roleId + '/member/' + userId)
                .then(function (response) {
                    $log.info('Successfully removed member from role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to remove member from role', response);
                    return $q.reject(response);
                });
        }

        function removeOnePermissionFromRole(roleId, permissionId) {
            return $http
                ['delete']('/api/role/' + roleId + '/permission/' + permissionId)
                .then(function (response) {
                    $log.info('Successfully removed permission from role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to remove permission from role', response);
                    return $q.reject(response);
                });
        }

        function updateOne(role) {
            return $http
                .put('/api/role/' + role.id, role)
                .then(function (response) {
                    $log.info('Successfully updated role', response.data);
                    return response.data;
                })
                .catch(function (response) {
                    $log.error('Failed to update role', response);
                    return $q.reject(response);
                });
        }
    }
})();