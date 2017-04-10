(function() {
    angular.module('mtn').factory('UserService', UserService);

    function UserService($http, $log, $q, $mdDialog, Cache) {
        return {
            findOne: findOne,
            showProfile: showProfile
        };

        //////////////////////////

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

