(function() {
    angular.module('mtn').factory('AuthInterceptor', AuthInterceptor);

    function AuthInterceptor(Cache) {
        var interceptor = {
            request: request
        };

        return interceptor;

        ///////////////////////////

        function request(config) {
            var token = Cache.get('id_token');
            if (token) {
                config.headers.Authorization = 'Bearer ' + token;
            }

            var accessToken = Cache.get('access_token');
            if (accessToken) {
                config.headers['mtn-access-token'] = accessToken;
            }

            return config;
        }
    }
})();