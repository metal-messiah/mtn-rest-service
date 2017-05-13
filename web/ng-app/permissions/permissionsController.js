(function () {
    'use strict';

    angular.module('mtn').controller('PermissionsController', PermissionsController);

    function PermissionsController($mdDialog, Permissions, Spinner, Security) {
        var vm = this;

        vm.check = Security.check;
        vm.openEditDialog = openEditDialog;

        init();

        //////////////////////////

        function init() {
            loadPermissions();
        }

        function loadPermissions() {
            vm.permissions = null;

            Spinner.start('permissions');

            Permissions
                .findAll()
                .then(function (permissions) {
                    vm.permissions = permissions;
                })
                .finally(function () {
                    Spinner.stop('permissions');
                });
        }

        function openEditDialog(permission) {
            if (Security.check('PERMISSIONS_UPDATE')) {
                return $mdDialog
                    .show({
                        templateUrl: 'permissions/edit-permission-dialog.html',
                        controller: EditPermissionController,
                        controllerAs: 'vm',
                        bindToController: true,
                        targetEvent: event,
                        clickOutsideToClose: false,
                        locals: {
                            permission: permission
                        }
                    })
                    .finally(loadPermissions);
            }
        }

        function EditPermissionController($mdDialog, Permissions, Spinner, Security) {
            var vm = this;

            vm.cancel = $mdDialog.cancel;
            vm.check = Security.check;
            vm.save = save;

            init();

            /////////////////////////

            function init() {
                vm.originalPermissionName = vm.permission.displayName;
            }

            function save() {
                Spinner.start('save');

                Permissions
                    .updateOne(vm.permission)
                    .then(function () {
                        $mdDialog.hide(vm.permission);
                    })
                    .finally(function () {
                        Spinner.stop('save');
                    });
            }
        }
    }
})();