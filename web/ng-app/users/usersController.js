(function () {
    'use strict';

    angular.module('mtn').controller('UsersController', UsersController);

    function UsersController($mdDialog, Users, Roles, Groups, Spinner, Security) {
        var vm = this;

        vm.check = Security.check;
        vm.openAddDialog = openAddDialog;
        vm.openEditDialog = openEditDialog;

        init();

        //////////////////////////

        function init() {
            loadUsers();
            loadRoles();
            loadGroups();
        }

        function loadGroups() {
            vm.groups = null;

            Groups
                .findAll()
                .then(function (groups) {
                    vm.groups = groups;
                })
        }

        function loadRoles() {
            vm.roles = null;

            Roles
                .findAll()
                .then(function (roles) {
                    vm.roles = roles;
                });
        }

        function loadUsers() {
            vm.users = null;

            Spinner.start('users');

            Users
                .findAll()
                .then(function (users) {
                    vm.users = users;
                })
                .finally(function () {
                    Spinner.stop('users');
                });
        }

        function openAddDialog(event) {
            Spinner.start('add-user');

            return $mdDialog
                .show({
                    templateUrl: 'users/add-user-dialog.html',
                    controller: AddUserController,
                    controllerAs: 'vm',
                    bindToController: true,
                    targetEvent: event,
                    clickOutsideToClose: false,
                    locals: {
                        groups: vm.groups,
                        roles: vm.roles,
                        users: vm.users
                    }
                })
                .finally(function () {
                    Spinner.stop('add-user');
                    loadUsers();
                });
        }

        function openEditDialog(user, event) {
            if (Security.check('USERS_UPDATE')) {
                return Users
                    .findOne(user.id)
                    .then(function (user) {
                        $mdDialog
                            .show({
                                templateUrl: 'users/edit-user-dialog.html',
                                controller: EditUserController,
                                controllerAs: 'vm',
                                bindToController: true,
                                targetEvent: event,
                                clickOutsideToClose: false,
                                locals: {
                                    groups: vm.groups,
                                    roles: vm.roles,
                                    users: vm.users,
                                    user: user
                                }
                            })
                            .finally(loadUsers);
                    });
            }
        }

        function AddUserController($mdDialog, Users, Spinner, Toaster) {
            var vm = this;

            vm.cancel = $mdDialog.cancel;
            vm.save = save;

            ///////////////////////////

            function checkForExistingUser() {
                return !!_.find(vm.users, function (user) {
                    return user.email === vm.user.email;
                });
            }

            function save() {
                if (checkForExistingUser()) {
                    Toaster.toast('A User already exists with this email');
                    return;
                }

                Spinner.start('save');

                Users
                    .addOne(vm.user)
                    .then($mdDialog.hide)
                    .finally(function () {
                        Spinner.stop('save');
                    });
            }
        }

        function EditUserController($mdDialog, $timeout, Users, Spinner, Toaster, Security) {
            var vm = this;

            vm.cancel = $mdDialog.cancel;
            vm.check = Security.check;
            vm.remove = remove;
            vm.save = save;

            init();

            ///////////////////////////

            function checkForExistingUser() {
                return !!_.find(vm.users, function (user) {
                    return user.email === vm.user.email
                        && user.id !== vm.user.id;
                });
            }

            function init() {
                vm.originalUserEmail = vm.user.email;

                //Get correct reference to group
                if (vm.user.group) {
                    vm.user.group = _.find(vm.groups, function (group) {
                        return group.id === vm.user.group.id;
                    });
                }

                //Get correct reference to role
                if (vm.user.role) {
                    vm.user.role = _.find(vm.roles, function (role) {
                        return role.id === vm.user.role.id;
                    });
                }
            }

            function remove() {
                if (vm.deleteTriggered) {
                    $timeout.cancel(vm.deleteTimer);
                    Spinner.start('delete');
                    Users
                        .deleteOne(vm.user.id)
                        .then(function () {
                            $mdDialog.hide();
                            Toaster.toast('Successfully deleted User');
                        })
                        .catch(function () {
                            Toaster.toast('An error occurred deleting the User');
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

            function save() {
                if (checkForExistingUser()) {
                    Toaster.toast('A User already exists with this email');
                    return;
                }

                Spinner.start('save');

                Users
                    .updateOne(vm.user)
                    .then($mdDialog.hide)
                    .finally(function () {
                        Spinner.stop('save');
                    });
            }
        }
    }
})();