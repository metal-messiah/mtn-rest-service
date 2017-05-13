(function () {
    'use strict';

    angular.module('mtn').factory('Toaster', Toaster);

    function Toaster($mdToast) {
        var service = {
            error: error,
            info: info,
            success: success,
            warn: warn
        };

        return service;

        //////////////////////////

        function error(message) {
            return toast('ERROR', message);
        }

        function info(message) {
            return toast('INFO', message);
        }

        function success(message) {
            return toast('SUCCESS', message);
        }

        function warn(message) {
            return toast('WARN', message);
        }

        function toast(level, message) {
            return $mdToast.show({
                hideDelay: 3000,
                position: 'bottom right',
                controller: ToastCtrl,
                controllerAs: 'vm',
                bindToController: true,
                templateUrl: 'core/toast/toast.html',
                toastClass: 'mtn-toast',
                locals: {
                    level: level,
                    message: message
                }
            });
        }

        function ToastCtrl() {
            var vm = this;

            switch (vm.level) {
                case 'INFO':
                case 'SUCCESS':
                    vm.iconName = 'check';
                    break;
                default:
                    vm.iconName = 'priority_high';
                    break;
            }
        }
    }
})();