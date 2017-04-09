(function() {
    angular.module('mtn').factory('AuthService', AuthService);

    function AuthService($log, $q, lock, authManager, Cache) {
        var service = {
            data: {},
            loadAuthProfile: loadAuthProfile,
            login: login,
            logout: logout,
            registerAuthenticationListener: registerAuthenticationListener
        };

        return service;

        ////////////////////////////

        function loadAuthProfile() {
            var deferred = $q.defer();

            lock.getProfile(Cache.get('id_token'), function(error, profile) {
                if (error) {
                    return $log.error('Failed to retrieve profile', error);
                }

                var user = AuthUser.build(profile);
                user.idToken = Cache.get('id_token');
                user.accessToken = Cache.get('access_token');

                $log.info('Successfully retrieved user auth profile', user);

                Cache.store('user', user);
            });

            return deferred.promise;
        }

        function login() {
            lock.show();
        }

        function logout() {
            Cache.clear();
            localStorage.removeItem('id_token');
            localStorage.removeItem('access_token');
            authManager.unauthenticate();
        }

        function registerAuthenticationListener() {
            lock.on('authenticated', function(authResult) {
                Cache.store('id_token', authResult.idToken);
                Cache.store('access_token', authResult.accessToken);
                service.loadAuthProfile();
                authManager.authenticate();
            });
        }
    }
})();