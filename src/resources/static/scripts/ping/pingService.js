(function() {
    angular.module('mtn').factory('PingService', PingService);

    function PingService($http, $q, $log) {
        var service = {
            ping: ping
        };

        return service;

        /////////////////////////

        function ping() {
            return $http.get('/api/ping').then(
                function(response) {
                    $log.info('Successfully retrieved ping', response.data);
                    return response.data;
                },
                function(response) {
                    $log.error('Failed to retrieve ping', response);
                    return $q.reject(response);
                }
            )
        }
    }
})();