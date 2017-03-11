(function() {
    angular.module('mtn').controller('IndexCtrl', IndexCtrl);

    function IndexCtrl($scope, AuthService, PingService) {

        $scope.getPing = function() {
            $scope.pingResult = 'Checking...';

            PingService.ping().then(
                function(ping) {
                    $scope.pingResult = ping.status;
                },
                function(response) {
                    $scope.pingResult = response.statusText;
                }
            );
        };

        function init() {
            $scope.AuthService = AuthService;
        }
        init();
    }
})();