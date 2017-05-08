(function () {
    'use strict';

    angular.module('mtn', ['ngRoute', 'ngAnimate', 'ngAria', 'ngSanitize', 'ngMessages', 'ngMaterial']);

    angular.module('mtn').config(config);

    ///////////////////////////////

    function config($locationProvider, $mdThemingProvider, $routeProvider) {
        $locationProvider.html5Mode(true);

        $mdThemingProvider.theme('default')
            .primaryPalette('indigo')
            .accentPalette('blue')
            .backgroundPalette('blue-grey')
            .warnPalette('red');

        $routeProvider
            .when('/groups', {controller: 'GroupsController', controllerAs: 'vm', templateUrl: 'groups/groups.html'})
            .when('/permissions', {
                controller: 'PermissionsController',
                controllerAs: 'vm',
                templateUrl: 'permissions/permissions.html'
            })
            .when('/roles', {controller: 'RolesController', controllerAs: 'vm', templateUrl: 'roles/roles.html'})
            .when('/users', {controller: 'UsersController', controllerAs: 'vm', templateUrl: 'users/users.html'})
            .otherwise({redirectTo: '/'});
    }
})();