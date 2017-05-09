(function () {
    'use strict';

    angular.module('mtn').controller('RolesController', RolesController);

    function RolesController($mdDialog, Roles, Permissions, Spinner) {
        var vm = this;

        vm.openAddDialog = openAddDialog;
        vm.openCopyDialog = openCopyDialog;
        vm.openEditDialog = openEditDialog;

        init();

        //////////////////////////

        function init() {
            loadRoles();
        }

        function loadRoles() {
            vm.roles = null;

            Spinner.start('roles');

            Roles
                .findAll()
                .then(function (roles) {
                    vm.roles = roles;
                })
                .finally(function () {
                    Spinner.stop('roles');
                });
        }

        function openAddDialog(event) {
            Spinner.start('add-role');

            Permissions
                .findAll()
                .then(function (permissions) {
                    $mdDialog
                        .show({
                            templateUrl: 'roles/add-role-dialog.html',
                            controller: AddRoleController,
                            controllerAs: 'vm',
                            bindToController: true,
                            targetEvent: event,
                            clickOutsideToClose: false,
                            locals: {
                                roles: vm.roles,
                                permissions: permissions
                            }
                        })
                        .finally(loadRoles);
                })
                .finally(function () {
                    Spinner.stop('add-role');
                });
        }

        function openCopyDialog(event) {
            return $mdDialog
                .show({
                    templateUrl: 'roles/copy-role-dialog.html',
                    controller: CopyRoleController,
                    controllerAs: 'vm',
                    bindToController: true,
                    targetEvent: event,
                    clickOutsideToClose: false,
                    locals: {
                        roles: roles
                    }
                })
                .finally(loadRoles);
        }

        function openEditDialog(role, event) {
            return $mdDialog
                .show({
                    templateUrl: 'roles/edit-role-dialog.html',
                    controller: EditRoleController,
                    controllerAs: 'vm',
                    bindToController: true,
                    targetEvent: event,
                    clickOutsideToClose: false,
                    locals: {
                        roles: roles,
                        role: role
                    }
                })
                .finally(loadRoles);
        }

        function AddRoleController($mdDialog, Roles, Users, Spinner, Toaster) {
            var vm = this;

            vm.addMember = addMember;
            vm.cancel = $mdDialog.cancel;
            vm.removeMember = removeMember;
            vm.save = save;
            vm.searchUsers = searchUsers;
            vm.togglePermission = togglePermission;

            init();

            //////////////////////////////

            function addMember() {
                vm.role.members.push(vm.selectedUser);
                vm.selectedUser = null;
                vm.searchText = null;
            }

            function checkForExistingRole() {
                return !!_.find(vm.roles, function (role) {
                    return role.displayName.toLowerCase() === vm.role.displayName.toLowerCase();
                });
            }

            function init() {
                vm.role = {
                    members: [],
                    permissions: []
                }
            }

            function removeMember(user) {
                vm.role.members.splice(vm.role.members.indexOf(user), 1);
            }

            function save() {
                if (checkForExistingRole()) {
                    Toaster.toast('A Role already exists with this name');
                    return;
                }

                Spinner.start('save');

                Roles
                    .addOne(vm.role)
                    .then($mdDialog.hide)
                    .finally(function () {
                        Spinner.stop('save');
                    });
            }

            function searchUsers(value) {
                return Users.findAll(value);
            }

            function togglePermission(permission) {
                if (permission.isAssigned) {
                    vm.role.permissions.push(permission);
                } else {
                    var index = _.findIndex(vm.role.permissions, function (assignedPermission) {
                        return assignedPermission.id === permission.id;
                    });
                    vm.role.permissions.splice(index, 1);
                }
            }
        }

        function CopyRoleController($mdDialog, Roles, Spinner, Toaster) {
            var vm = this;

            vm.cancel = $mdDialog.cancel;
            vm.save = save;

            //////////////////////////////


        }

        function EditRoleController($mdDialog, Roles, Spinner, Toaster) {
            var vm = this;

            vm.cancel = $mdDialog.cancel;
            vm.save = save;

            //////////////////////////////


        }
    }
})();