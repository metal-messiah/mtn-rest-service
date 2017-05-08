(function () {
    angular.module('mtn').factory('Spinner', Spinner);

    /**
     * Since I can't use the ECMA6 Set yet...
     */
    function Set() {
        var _items = [];

        this.add = function (value) {
            if (value && !_.includes(_items, value)) {
                _items.push(value);
                this.size = _items.length;
            }
        };

        this.delete = function (value) {
            if (_.includes(_items, value)) {
                _items.splice(_items.indexOf(value), 1);
                this.size = _items.length;
            }
        };

        this.has = function (value) {
            return value && _.includes(_items, value);
        };

        this.size = _items.length;
    }

    function Spinner() {
        var service = {
            spinners: new Set(),
            start: start,
            stop: stop,
            isSpinning: isSpinning
        };

        return service;

        //////////////////////

        function start(name) {
            if (typeof name === 'string') {
                service.spinners.add(name);
            }
        }

        function stop(name) {
            if (typeof name === 'string') {
                service.spinners.delete(name);
            }
        }

        function isSpinning(name) {
            return typeof name === 'string' && service.spinners.has(name);
        }
    }
})();