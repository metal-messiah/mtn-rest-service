(function () {
    'use strict';

    angular.module('mtn').factory('Auth', Auth);

    function Auth($log, $http, $q, lock, authManager, Cache) {
        var API_CLIENT_ID = 'UtMThNghwlK13gzUuRXeF3kUHMQMGMiX';

        var service = {
            getApiAccessToken: getApiAccessToken,
            getUserProfile: getUserProfile,
            isAuthenticated: isAuthenticated,
            login: login,
            logout: logout,
            registerAuthenticationListener: registerAuthenticationListener,
        };

        return service;

        //////////////////////////

        //TODO show/hide UI elements appropriately
        //TODO create a model for the current user with permission checks
        //TODO test login page behavior
        //TODO test expired token response behavior
        //TODO get rid of the identity tables, models, etc, as they are no longer needed with the new flow

        /**
         * Get the API Access Token and cache it.
         */
        function getApiAccessToken() {
            var config = {
                headers: {
                    'mtn-client-id': API_CLIENT_ID
                }
            };

            return $http
                .get('/api/auth/token', config)
                .then(function (response) {
                    Cache.store('api_access_token', response.data.token);
                    return response.data.token;
                })
                .catch(function (response) {
                    $log.error('Failed to retrieve API Access Token', response);
                    return $q.reject(response);
                });
        }

        function getUserProfile() {
            return service
                .getApiAccessToken()
                .then(function () {
                    return $http
                        .get('/api/auth/user')
                        .then(function (response) {
                            $log.info('Successfully retrieved User Profile', response.data);
                            Cache.store('user', response.data);
                        })
                        .catch(function (response) {
                            $log.error('Failed to retrieve User Profile', response);
                            return $q.reject(response);
                        });
                });
        }

        function isAuthenticated() {
            return authManager.isAuthenticated() && Cache.user();
        }

        function login() {
            lock.show();
        }

        function logout() {
            Cache.clear();
            authManager.unauthenticate();
        }

        function registerAuthenticationListener() {
            lock.on('authenticated', function (authResult) {
                Cache.store('id_token', authResult.idToken);
                Cache.store('access_token', authResult.accessToken);
                $log.info('Token issued at: ' + new Date(authResult.idTokenPayload.iat));
                $log.info('Current time: ' + new Date());
                service.getUserProfile();
                authManager.authenticate();
            });

            lock.on('authorization_error', function (error) {
                $log.error('Failed to authenticate user', error);
            });
        }
    }
})();