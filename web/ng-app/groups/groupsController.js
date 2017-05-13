(function () {
    'use strict';

    angular.module('mtn').controller('GroupsController', GroupsController);

    function GroupsController($mdDialog, Groups, Spinner, Security) {
        var vm = this;

        vm.check = Security.check;
        vm.openAddDialog = openAddDialog;
        vm.openEditDialog = openEditDialog;

        init();

        //////////////////////////

        function init() {
            loadGroups();
        }

        function loadGroups() {
            vm.groups = null;

            Spinner.start('groups');

            Groups
                .findAll()
                .then(function (groups) {
                    vm.groups = groups;
                })
                .finally(function () {
                    Spinner.stop('groups');
                });
        }

        function openAddDialog(event) {
            return $mdDialog
                .show({
                    templateUrl: 'groups/add-group-dialog.html',
                    controller: AddGroupController,
                    controllerAs: 'vm',
                    bindToController: true,
                    targetEvent: event,
                    clickOutsideToClose: false,
                    locals: {
                        groups: vm.groups
                    }
                })
                .finally(loadGroups);
        }

        function openEditDialog(group, event) {
            if (Security.check('GROUPS_UPDATE')) {
                return Groups
                    .findOne(group.id)
                    .then(function (group) {
                        $mdDialog
                            .show({
                                templateUrl: 'groups/edit-group-dialog.html',
                                controller: EditGroupController,
                                controllerAs: 'vm',
                                bindToController: true,
                                targetEvent: event,
                                clickOutsideToClose: false,
                                locals: {
                                    groups: vm.groups,
                                    group: group
                                }
                            })
                            .finally(loadGroups);
                    });
            }
        }

        function AddGroupController($mdDialog, Groups, Users, Spinner, Toaster) {
            var vm = this;

            vm.addMember = addMember;
            vm.cancel = $mdDialog.cancel;
            vm.removeMember = removeMember;
            vm.save = save;
            vm.searchUsers = searchUsers;

            init();

            ////////////////////////////////

            function addMember() {
                if (vm.selectedUser) {
                    vm.group.members.push(vm.selectedUser);
                    vm.selectedUser = null;
                    vm.searchText = null;
                }
            }

            function checkForExistingGroup() {
                return !!_.find(vm.groups, function (group) {
                    return group.displayName.toLowerCase() === vm.group.displayName.toLowerCase();
                });
            }

            function init() {
                vm.group = {
                    members: []
                };
            }

            function removeMember(user) {
                vm.group.members.splice(vm.group.members.indexOf(user), 1);
            }

            function save() {
                if (checkForExistingGroup()) {
                    Toaster.warn('A Group already exists with this name');
                    return;
                }

                Spinner.start('save');

                Groups
                    .addOne(vm.group)
                    .then(function (group) {
                        vm.group.id = group.id;
                        //Now that we've created the group, add the members
                        return Groups.updateOne(vm.group);
                    })
                    .then($mdDialog.hide)
                    .finally(function () {
                        Spinner.stop('save');
                    });
            }

            function searchUsers(value) {
                return Users.findAll(value);
            }
        }

        function EditGroupController($mdDialog, $timeout, Groups, Users, Spinner, Toaster, Security) {
            var vm = this;

            vm.addMember = addMember;
            vm.cancel = $mdDialog.cancel;
            vm.check = Security.check;
            vm.remove = remove;
            vm.removeMember = removeMember;
            vm.save = save;
            vm.searchUsers = searchUsers;

            init();

            ////////////////////////////////

            function addMember() {
                if (vm.selectedUser) {
                    vm.group.members.push(vm.selectedUser);
                    vm.selectedUser = null;
                    vm.searchText = null;
                }
            }

            function checkForExistingGroup() {
                return !!_.find(vm.groups, function (group) {
                    return group.displayName.toLowerCase() === vm.group.displayName.toLowerCase()
                        && group.id !== vm.group.id;
                });
            }

            function init() {
                vm.originalGroupName = vm.group.displayName;
                if (!vm.group.members) {
                    vm.group.members = [];
                }
            }

            function remove() {
                if (vm.deleteTriggered) {
                    $timeout.cancel(vm.deleteTimer);
                    Spinner.start('delete');
                    Groups
                        .deleteOne(vm.group.id)
                        .then(function () {
                            $mdDialog.hide();
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
                vm.group.members.splice(vm.group.members.indexOf(user), 1);
            }

            function save() {
                if (checkForExistingGroup()) {
                    Toaster.warn('A Group already exists with this name');
                    return;
                }

                Spinner.start('save');

                Groups
                    .updateOne(vm.group)
                    .then($mdDialog.hide)
                    .finally(function () {
                        Spinner.stop('save');
                    });
            }

            function searchUsers(value) {
                return Users.findAll(value);
            }
        }
    }
})();