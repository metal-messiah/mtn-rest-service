(function() {
    angular.module('mtn').factory('AuthService', AuthService);

    function AuthService($log, $q, $window, lock, authManager, Cache, UserService) {
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
            return loadAuthProfile()
                .then(function(profile) {
                    var userId = profile.user_id;

                    return UserService.findOne(userId.split('|')[1])
                        .then(function(user) {
                            user.idToken = Cache.get('id_token');
                            user.accessToken = Cache.get('access_token');

                            $log.info('Successfully retrieved user profile', user);

                            Cache.store('user', user);
                        });
                })
                .catch(function(error) {
                    $log.error('Failed to retrieve profile', error);
                    return $q.reject(error);
                });
        }

        function loadAuthProfile() {
            var deferred = $q.defer();

            lock.getProfile(Cache.get('id_token'), function(error, profile) {
                if (error) {
                    $log.error('Failed to retrieve user auth profile', error);
                    deferred.reject(error);
                }

                $log.info('Successfully retrieved user auth profile', profile);
                deferred.resolve(profile);
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
                service.loadProfile();
                authManager.authenticate();
            });
        }
    }
})();