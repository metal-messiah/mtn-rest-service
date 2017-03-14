(function() {
    angular.module('mtn').factory('AuthService', AuthService);

    var ID_TOKEN = 'id_token';

    function AuthService($log, $q, lock, authManager, Cache) {
        var service = {
            data: {},
            loadProfile: loadProfile,
            login: login,
            logout: logout,
            registerAuthenticationListener: registerAuthenticationListener
        };

        return service;

        ////////////////////////////



        function loadProfile() {
            var deferred = $q.defer();

            lock.getProfile(getToken(), function(error, profile) {
                if (error) {
                    return $log.error('Failed to retrieve profile', error);
                }

                $log.info('Successfully retrieved user profile', profile);

                Cache.store('user', profile);
            });

            return deferred.promise;
        }

        function login() {
            lock.show();
        }

        function logout() {
            Cache.clear();
            localStorage.removeItem(ID_TOKEN);
            authManager.unauthenticate();
        }

        function registerAuthenticationListener() {
            lock.on('authenticated', function(authResult) {
                setToken(authResult.idToken);
                Cache.store('access_token', authResult.accessToken);
                service.loadProfile();
                authManager.authenticate();
            });
        }

        //////////////////////////////

        function setToken(value) {
            Cache.store(ID_TOKEN, value);
        }

        function getToken() {
            return Cache.get(ID_TOKEN);
        }
    }
})();