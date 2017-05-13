angular.module('mtn').component('sideNav', {
    templateUrl: 'nav/side-nav.html',
    controller: SideNavController,
    controllerAs: 'vm'
});

function SideNavController(Security) {
    var vm = this;

    vm.check = Security.check;
}