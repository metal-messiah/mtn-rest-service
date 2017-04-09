angular.module('mtn').component('mainNav', {
    templateUrl: 'template/main-nav.html',
    controller: MainNavController,
    controllerAs: 'vm'
});

function MainNavController(AuthService, Cache) {
    var vm = this;
    vm.login = AuthService.login;
    vm.logout = AuthService.logout;

    vm.user = function() {
        return Cache.user();
    };
}