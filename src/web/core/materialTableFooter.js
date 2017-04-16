(function() {
    angular.module( 'mtn' ).component( 'materialTableFooter', {
        template: '' +
        '<div class="material-table-footer" layout="row" layout-align="end center">' +
        '   <div>Showing {{vm.paginator.currentPageRange()}} of {{vm.paginator.totalResults}}</div>' +
        '   <md-button class="md-icon-button" ng-click="vm.paginator.first()" ng-disabled="!vm.paginator.canPrevious()">' +
        '       <md-icon><i class="material-icons">first_page</i></md-icon>' +
        '   </md-button>' +
        '   <md-button class="md-icon-button" ng-click="vm.paginator.previous()" ng-disabled="!vm.paginator.canPrevious()">' +
        '       <md-icon><i class="material-icons">chevron_left</i></md-icon>' +
        '   </md-button>' +
        '   <div>Page {{vm.paginator.pageNumber + 1}} of {{vm.paginator.totalPages}}</div>' +
        '   <md-button class="md-icon-button wrapper" ng-click="vm.paginator.next()" ng-disabled="!vm.paginator.canNext()">' +
        '       <md-icon><i class="material-icons">chevron_right</i></md-icon>' +
        '   </md-button>' +
        '   <md-button class="md-icon-button" ng-click="vm.paginator.last()" ng-disabled="!vm.paginator.canNext()">' +
        '       <md-icon><i class="material-icons">last_page</i></md-icon>' +
        '   </md-button>',
        controller: MaterialTableFooterCtrl,
        controllerAs: 'vm',
        bindings: {
            paginator: '='
        }
    } );

    function MaterialTableFooterCtrl() {
        var vm = this;
    }
})();