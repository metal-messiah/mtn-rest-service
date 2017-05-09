(function () {
    'use strict';

    angular.module('mtn').factory('Users', Users);

    function Users($http) {
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
                    return response.data;
                });
        }

        function deleteOne(id) {
            return $http['delete']('/api/group/' + id);
        }

        function findAll(q) {
            var config = new DefaultParams();

            if (q) {
                config.add('q', q);
            }

            return $http
                .get('/api/group', config)
                .then(function (response) {
                    return response.data.content;
                });
        }

        function findOne(id) {
            return $http
                .get('/api/group/' + id)
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