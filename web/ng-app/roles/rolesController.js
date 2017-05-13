(function () {
    'use strict';

    angular.module('mtn').controller('RolesController', RolesController);

    function RolesController($mdDialog, Roles, Permissions, Spinner, Security) {
        var vm = this;

        vm.check = Security.check;
        vm.openAddDialog = openAddDialog;
        vm.openEditDialog = openEditDialog;

        init();

        //////////////////////////

        function init() {
            loadRoles();
            loadPermissions();
        }

        function loadPermissions() {
            vm.permissions = null;

            Permissions
                .findAll()
                .then(function (permissions) {
                    vm.permissions = permissions;
                });
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

            return $mdDialog
                .show({
                    templateUrl: 'roles/add-role-dialog.html',
                    controller: AddRoleController,
                    controllerAs: 'vm',
                    bindToController: true,
                    targetEvent: event,
                    clickOutsideToClose: false,
                    locals: {
                        roles: vm.roles,
                        permissions: vm.permissions
                    }
                })
                .finally(function () {
                    loadRoles();
                    Spinner.stop('add-role');
                });
        }

        function openEditDialog(role, event) {
            if (Security.check('ROLES_UPDATE')) {
                return Roles
                    .findOne(role.id)
                    .then(function (role) {
                        $mdDialog
                            .show({
                                templateUrl: 'roles/edit-role-dialog.html',
                                controller: EditRoleController,
                                controllerAs: 'vm',
                                bindToController: true,
                                targetEvent: event,
                                clickOutsideToClose: false,
                                locals: {
                                    permissions: vm.permissions,
                                    roles: vm.roles,
                                    role: role
                                }
                            })
                            .finally(loadRoles);
                    });
            }
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
                if (vm.selectedUser) {
                    vm.role.members.push(vm.selectedUser);
                    vm.selectedUser = null;
                    vm.searchText = null;
                }
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
                    .then(function (role) {
                        vm.role.id = role.id;
                        //Now that we've created the role, add members and permissions
                        return Roles.updateOne(vm.role);
                    })
                    .then($mdDialog.hide)
                    .then(function () {
                        Toaster.toast('Successfully added Role');
                    })
                    .catch(function () {
                        Toaster.toast('An error occurred adding the Role');
                    })
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

        function EditRoleController($mdDialog, $timeout, Roles, Users, Spinner, Toaster, Security) {
            var vm = this;

            vm.addMember = addMember;
            vm.cancel = $mdDialog.cancel;
            vm.check = Security.check;
            vm.remove = remove;
            vm.removeMember = removeMember;
            vm.save = save;
            vm.searchUsers = searchUsers;
            vm.togglePermission = togglePermission;

            init();

            //////////////////////////////

            function addMember() {
                if (vm.selectedUser) {
                    vm.role.members.push(vm.selectedUser);
                    vm.selectedUser = null;
                    vm.searchText = null;
                }
            }

            function checkForExistingRole() {
                return !!_.find(vm.roles, function (role) {
                    return role.displayName.toLowerCase() === vm.role.displayName.toLowerCase()
                        && role.id !== vm.role.id;
                });
            }

            function init() {
                vm.originalRoleName = vm.role.displayName;

                if (!vm.role.members) {
                    vm.role.members = [];
                }
                if (!vm.role.permissions) {
                    vm.role.permissions = [];
                }

                for (var i = 0; i < vm.permissions.length; i++) {
                    var permission = vm.permissions[i];
                    permission.isAssigned = !!_.find(vm.role.permissions, function (assignedPermission) {
                        return assignedPermission.id === permission.id;
                    });
                }
            }

            function remove() {
                if (vm.deleteTriggered) {
                    $timeout.cancel(vm.deleteTimer);
                    Spinner.start('delete');
                    Roles
                        .deleteOne(vm.role.id)
                        .then(function () {
                            $mdDialog.hide();
                            Toaster.toast('Successfully deleted Role');
                        })
                        .catch(function () {
                            Toaster.toast('An error occurred deleting the Role');
                        })
                        .finally(function () {
                            Spinner.stop('delete');
                        });
                }
                else {
                    vm.deleteTriggered = true;
                    vm.deleteTimer = $timeout(function () {
                        vm.deleteTriggered = false;
                    }, 1000);
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
                    .updateOne(vm.role)
                    .then($mdDialog.hide)
                    .then(function () {
                        Toaster.toast('Successfully updated Role');
                    })
                    .catch(function () {
                        Toaster.toast('An error occurred updating the Role');
                    })
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
    }
})();