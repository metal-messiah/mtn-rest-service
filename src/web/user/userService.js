(function() {
    angular.module('mtn').factory('UserService', UserService);

    function UserService($http, $log, $q, $mdDialog, Cache) {
        return {
            findAll: findAll,
            findOne: findOne,
            showProfile: showProfile
        };

        //////////////////////////

        function findAll(params) {
            var config = {
                params: {}
            };

            for (var property in params) {
                if (params.hasOwnProperty(property) && params[property]) {
                    config.params[property] = params[property];
                }
            }

            return $http.get('/api/user', config)
                .then(function(response) {
                    var results = new GenericPaginatedResponse();

                    if (response.data.content) {
                        for (var i = 0; i < response.data.content.length; i++) {
                            results.content.push(UserProfile.build(response.data.content[i]));
                        }
                    }

                    if (response.data.pagination) {
                        results.pagination = response.data.pagination;
                    }

                    return results;
                })
                .catch(function(response) {
                    $log.error('Failed to retrieve users', response);
                    return $q.reject(response);
                });
        }

        function findOne(id) {
            return $http.get('/api/user/' + id)
                .then(function(response) {
                    return UserProfile.build(response.data);
                })
                .catch(function(response) {
                    $log.error('Failed to retrieve user', response);
                    return $q.reject(response);
                });
        }

        function showProfile(event) {
            return $mdDialog.show({
                controller: UserProfileDialogCtrl,
                controllerAs: 'vm',
                templateUrl: 'user/user-profile-dialog.html',
                targetEvent: event,
                clickOutsideToClose:true
            });
        }

        function UserProfileDialogCtrl($mdDialog, Cache) {
            var vm = this;

            vm.user = Cache.user();

            vm.cancel = function() {
                $mdDialog.cancel();
            };
        }
    }
})();

