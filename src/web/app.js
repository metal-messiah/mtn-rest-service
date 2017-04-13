angular.module('mtn', ['ngRoute', 'ngAnimate', 'ngAria', 'ngAnimate', 'ngMessages', 'ngSanitize', 'ngMaterial', 'auth0.lock', 'angular-jwt']);

angular.module('mtn').config(config);
angular.module('mtn').config(configAuth);
angular.module('mtn').run(registerAuth);

///////////////////////////////

function config($httpProvider, $locationProvider, $mdThemingProvider, $routeProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');

    $locationProvider.html5Mode(true);

    $mdThemingProvider.theme('default')
        .primaryPalette('indigo')
        .accentPalette('blue')
        .backgroundPalette('grey')
        .warnPalette('red')
        .dark();

    $routeProvider
        .when('/groups', {controller: 'GroupsCtrl', controllerAs: 'vm', templateUrl: 'group/groups.html'})
        .when('/permissions', {controller: 'PermissionsCtrl', controllerAs: 'vm', templateUrl: 'permission/permissions.html'})
        .when('/roles', {controller: 'RolesCtrl', controllerAs: 'vm', templateUrl: 'role/roles.html'})
        .when('/users', {controller: 'UsersCtrl', controllerAs: 'vm', templateUrl: 'user/users.html'})
        .otherwise({redirectTo: '/'});
}

function configAuth(lockProvider, jwtOptionsProvider) {
    lockProvider.init({
        clientID: 'FArOoQuRqPT1MZFsNE9qnxeykHp48cIO',
        domain: 'asudweeks.auth0.com'
    });

    jwtOptionsProvider.config({
        tokenGetter: function() {
            return JSON.parse(localStorage.getItem('id_token'));
        }
    });
}

function registerAuth($rootScope, AuthService, lock, authManager, Cache) {
    AuthService.registerAuthenticationListener();
    lock.interceptHash();
    authManager.checkAuthOnRefresh();
    if (authManager.isAuthenticated()) {
        AuthService.loadProfile();
    } else {
        Cache.clear();
    }

    $rootScope.Cache = Cache;
}