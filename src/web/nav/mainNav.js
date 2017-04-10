angular.module('mtn').component('mainNav', {
    templateUrl: 'nav/main-nav.html',
    controller: MainNavController,
    controllerAs: 'vm'
});

function MainNavController(AuthService, UserService, Cache) {
    var vm = this;
    vm.login = AuthService.login;
    vm.logout = AuthService.logout;

    vm.user = function() {
        return Cache.user();
    };

    vm.openProfile = function(event) {
        UserService.showProfile(event);
    };
}