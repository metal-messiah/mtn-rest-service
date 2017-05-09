(function () {
    'use strict';

    angular.module('mtn').component('userChip', {
        template: '' +
        '<div class="user-chip md-whiteframe-1dp" layout="row" layout-align="start center" style="margin:2px; position:relative;">' +
        '     <md-icon class="material-icons" style="margin:0 6px;">person</md-icon>' +
        '     <div flex>' +
        '         <div style="font-size:12px;"><strong>{{::vm.user.email}}</strong></div>' +
        '         <div style="font-size:10px; color:#aaa;">{{::vm.user.firstName}} {{::vm.user.lastName}}</div>' +
        '     </div>' +
        '     <md-button class="md-icon-button" ng-click="vm.doDelete()" ng-if="::vm.doDelete">' +
        '         <md-icon class="material-icons" style="color:red">cancel</md-icon>' +
        '         <md-tooltip ng-if="vm.deleteTooltip">{{::vm.deleteTooltip}}</md-tooltip>' +
        '     </md-button>' +
        '     <named-spinner name="{{::(\'user-chip-\' + vm.user.id)}}" size="extra-small"></named-spinner>' +
        '</div>',
        controller: UserChipController,
        controllerAs: 'vm',
        bindings: {
            onDelete: '=',
            deleteTooltip: '@',
            user: '='
        }
    });

    function UserChipController() {
        var vm = this;

        vm.doDelete = doDelete;

        ////////////////////////////

        function doDelete() {
            if (typeof vm.onDelete === 'function') {
                vm.onDelete(vm.user);
            }
        }
    }
})();