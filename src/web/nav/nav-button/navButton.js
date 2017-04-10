angular.module('mtn').component('navButton', {
    templateUrl: 'nav/nav-button/nav-button.html',
    controller: NavButtonController,
    controllerAs: 'vm',
    bindings: {
        label: '@',
        route: '@'
    }
});

function NavButtonController($location) {
    var vm = this;

    vm.navigate = function() {
        $location.path(vm.route);
    };

    vm.isActive = function() {
        return $location.path() === vm.route;
    };
}