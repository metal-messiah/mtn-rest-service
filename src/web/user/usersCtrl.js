angular.module('mtn').controller('UsersCtrl', UsersCtrl);

function UsersCtrl(UserService, PaginatorFactory) {
    var vm = this;

    vm.queryParams = {};

    vm.search = function() {
        vm.paginator = null;
        UserService.findAll(vm.queryParams).then(
            function(results) {
                vm.paginator = PaginatorFactory.create(results.content, results.pagination.totalResults, results.pagination.limit)
                    .setQueryFunction(UserService.findAll)
                    .setQueryParams(vm.queryParams);
            }
        );
    };

    vm.search();
}