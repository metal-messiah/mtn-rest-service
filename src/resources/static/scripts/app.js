angular.module('mtn', ['ngMaterial', 'auth0.lock', 'angular-jwt']);

angular.module('mtn').config(configureAuth);

angular.module('mtn').run(registerAuth);

/////////////////////////////////////

function configureAuth($httpProvider, $locationProvider, lockProvider, jwtOptionsProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');

    $locationProvider.html5Mode(true);

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
    }

    $rootScope.Cache = Cache;
}