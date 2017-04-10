angular.module('mtn').controller('IndexCtrl', IndexCtrl);

function IndexCtrl(Cache) {
    var vm = this;

    vm.user = function() {
        return Cache.user();
    };
}