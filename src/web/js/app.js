angular.module('mtn', ['ngRoute', 'ngAnimate', 'ngAria', 'ngAnimate', 'ngMessages', 'ngSanitize', 'ngMaterial', 'auth0.lock', 'angular-jwt']);

angular.module('mtn').config(config);
angular.module('mtn').config(configAuth);
angular.module('mtn').run(registerAuth);

///////////////////////////////

function config($httpProvider, $locationProvider, $mdThemingProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');

    $locationProvider.html5Mode(true);

    $mdThemingProvider.theme('default')
        .dark();
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
        AuthService.loadAuthProfile();
    }

    $rootScope.Cache = Cache;
}