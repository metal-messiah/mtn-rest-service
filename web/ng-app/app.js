(function () {
    'use strict';

    angular.module('mtn', ['ngRoute', 'ngAnimate', 'ngAria', 'ngSanitize', 'ngMessages', 'ngMaterial', 'auth0.lock', 'angular-jwt']);

    angular.module('mtn').config(config);
    angular.module('mtn').config(configAuth);
    angular.module('mtn').run(mtnInit);
    angular.module('mtn').run(registerAuth);

    ///////////////////////////////

    function config($httpProvider, $locationProvider, $mdThemingProvider, $routeProvider, $qProvider) {
        $httpProvider.interceptors.push('AuthRequestInterceptor');

        $locationProvider.html5Mode(true);

        $mdThemingProvider.theme('default')
            .primaryPalette('indigo')
            .accentPalette('blue')
            .backgroundPalette('blue-grey')
            .warnPalette('red');

        $routeProvider
            .when('/', {controller: 'DashboardController', controllerAs: 'vm', templateUrl: 'dashboard/dashboard.html'})
            .when('/groups', {controller: 'GroupsController', controllerAs: 'vm', templateUrl: 'groups/groups.html'})
            .when('/permissions', {
                controller: 'PermissionsController',
                controllerAs: 'vm',
                templateUrl: 'permissions/permissions.html'
            })
            .when('/roles', {controller: 'RolesController', controllerAs: 'vm', templateUrl: 'roles/roles.html'})
            .when('/users', {controller: 'UsersController', controllerAs: 'vm', templateUrl: 'users/users.html'})
            .when('/login', {controller: 'LoginController', controllerAs: 'vm', templateUrl: 'auth/login.html'})
            .otherwise({redirectTo: '/login'});

        $qProvider.errorOnUnhandledRejections(false);
    }

    function configAuth(lockProvider, jwtOptionsProvider) {
        lockProvider.init({
            clientID: 'FArOoQuRqPT1MZFsNE9qnxeykHp48cIO',
            domain: 'asudweeks.auth0.com',
            options: {
                redirectUrl: location.href.replace('/login', ''),
                closable: false
            }
        });

        jwtOptionsProvider.config({
            tokenGetter: function () {
                return JSON.parse(localStorage.getItem('id_token'));
            }
        });
    }

    function mtnInit($rootScope, Auth, Cache, Security) {
        $rootScope.Cache = Cache;
        $rootScope.isAuthenticated = Auth.isAuthenticated;
    }

    function registerAuth($location, lock, authManager, Auth, Cache) {
        Auth.registerAuthenticationListener();
        lock.interceptHash();
        authManager.checkAuthOnRefresh();
        if (authManager.isAuthenticated()) {
            Auth.getUserProfile();
        } else {
            Cache.clear();
            $location.path('/login');
        }
    }
})();