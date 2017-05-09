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

        function addOne(user) {
            return $http
                .post('/api/user', user)
                .then(function (response) {
                    return response.data;
                });
        }

        function deleteOne(id) {
            return $http['delete']('/api/user/' + id);
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

                    return response.data.content;
                });
        }

        function findOne(id) {
            return $http
                .get('/api/user/' + id)
                .then(function (response) {
                    return response.data;
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
                    return response.data;
                });
        }
    }
})();