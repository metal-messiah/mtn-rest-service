angular.module('mtn').component('mainNav', {
    templateUrl: 'nav/main-nav.html',
    controller: MainNavController,
    controllerAs: 'vm'
});

function MainNavController(Auth) {
    var vm = this;

    vm.isAuthenticated = Auth.isAuthenticated;
    vm.logout = Auth.logout;
}