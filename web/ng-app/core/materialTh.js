(function () {
    'use strict';

    angular.module('mtn').directive('materialTh', MaterialTh);

    function MaterialTh() {
        return {
            restrict: 'E',
            replace: true,
            template: '' +
            '<div class="material-th clickable" style="white-space:nowrap;" ng-click="toggleSort()" md-ink-ripple>' +
            '    <i class="material-icons md-18" ng-if="isCurrentSortColumn() && align === \'right\' && sortReverse">arrow_downward</i>' +
            '    <i class="material-icons md-18" ng-if="isCurrentSortColumn() && align === \'right\' && !sortReverse">arrow_upward</i>' +
            '    {{label}}' +
            '    <i class="material-icons md-18" ng-if="isCurrentSortColumn() && align === \'left\' && sortReverse">arrow_downward</i>' +
            '    <i class="material-icons md-18" ng-if="isCurrentSortColumn() && align === \'left\' && !sortReverse">arrow_upward</i>' +
            '</div>',
            scope: {
                sortOn: '=',
                sortReverse: '=',
                default: '=',
                valueRef: '@',
                label: '@',
                align: '@'
            },
            link: function (scope, element, attrs) {
                //Set default if applicable
                if (scope.default) {
                    scope.sortOn = scope.valueRef;
                }

                //Align left by default
                if (!scope.align) {
                    scope.align = 'left';
                }

                scope.isCurrentSortColumn = function () {
                    return scope.sortOn === scope.valueRef;
                };

                scope.toggleSort = function () {
                    if (scope.isCurrentSortColumn()) {
                        scope.sortReverse = !scope.sortReverse;
                    }
                    else {
                        scope.sortOn = scope.valueRef;
                        scope.sortReverse = false;
                    }
                };
            }
        }
    }
})();