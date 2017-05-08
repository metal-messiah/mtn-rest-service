(function () {
    'use strict';

    angular.module('mtn').component('namedSpinner', {
        template: '' +
        // '<div class="spinner-container" ng-if="vm.isActive()" ng-click="$event.stopPropagation()">' +
        '   <div class="spinner" layout="column" layout-align="center center" ng-if="vm.isActive()" ng-click="$event.stopPropagation()">' +
        '       <md-progress-circular class="md-accent" md-diameter="20"></md-progress-circular>' +
        '   </div>',
        // '</div>',
        controller: NamedSpinnerController,
        controllerAs: 'vm',
        bindings: {
            name: '@'
        }
    });

    function NamedSpinnerController(Spinner) {
        var vm = this;

        vm.isActive = isActive;

        ////////////////////////

        function isActive() {
            return Spinner.isSpinning(vm.name);
        }
    }
})();