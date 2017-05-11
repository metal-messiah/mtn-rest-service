(function () {
    'use strict';

    angular.module('mtn').factory('AuthRequestInterceptor', AuthRequestInterceptor);

    function AuthRequestInterceptor(Cache) {
        var interceptor = {
            request: request
        };

        return interceptor;

        ///////////////////////////

        function request(config) {
            if (config.url.indexOf('/api/') !== -1 && config.url !== '/api/auth/token') {
                var apiAccessToken = Cache.get('api_access_token');
                if (apiAccessToken) {
                    config.headers.Authorization = 'Bearer ' + apiAccessToken;
                }

                var accessToken = Cache.get('access_token');
                if (accessToken) {
                    config.headers['mtn-access-token'] = accessToken;
                }
            }

            return config;
        }
    }
})();