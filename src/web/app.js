angular.module('mtn', ['ngRoute', 'ngAnimate', 'ngAria', 'ngAnimate', 'ngMessages', 'ngSanitize', 'ngMaterial', 'auth0.lock', 'angular-jwt']);

angular.module('mtn').config(config);
angular.module('mtn').config(configAuth);
angular.module('mtn').run(registerAuth);

///////////////////////////////

function config($httpProvider, $locationProvider, $mdThemingProvider, $routeProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');

    $locationProvider.html5Mode(true);

    $mdThemingProvider
        .definePalette('custom-warn', {
            '50': '#ffffff',
            '100': '#ffffff',
            '200': '#ffffff',
            '300': '#ffffff',
            '400': '#fffbfc',
            '500': '#FCE4EC',
            '600': '#f9cddc',
            '700': '#f7b6cc',
            '800': '#f49fbc',
            '900': '#f288ab',
            'A100': '#ffffff',
            'A200': '#ffffff',
            'A400': '#ffffff',
            'A700': '#ef719b'
        });

    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('blue-grey')
        .backgroundPalette('grey')
        .warnPalette('custom-warn')
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