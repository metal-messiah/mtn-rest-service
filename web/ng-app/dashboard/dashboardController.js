(function () {
    'use strict';

    angular.module('mtn').controller('DashboardController', DashboardController);

    function DashboardController(Security) {
        var vm = this;

        vm.hasPermissionForAtLeastOneOption = hasPermissionForAtLeastOneOption;

        //////////////////////

        function hasPermissionForAtLeastOneOption() {
            return Security.checkAny(['GROUPS_READ', 'ROLES_READ', 'PERMISSIONS_READ', 'USERS_READ']);
        }
    }
})();