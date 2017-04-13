(function() {
    angular.module('mtn').factory('Cache', Cache);

    function Cache() {
        var service = {
            data: {},
            cache: cache,
            clear: clear,
            get: get,
            store: store,
            user: user
        };

        return service;

        //////////////////////////

        /**
         * Cache the given item in-memory-only
         */
        function cache(name, value) {
            if (typeof value !== 'undefined') {
                service.data[name] = value;
            }
        }

        /**
         * Retrieve the item, checking in-memory first, then local storage
         */
        function get(name) {
            if (typeof service.data[name] === 'undefined') {
                service.data[name] = JSON.parse(localStorage.getItem(name));
            }
            return service.data[name];
        }

        /**
         * Cache the given item in local storage
         */
        function store(name, value) {
            if (typeof value !== 'undefined') {
                localStorage.setItem(name, JSON.stringify(value));
                service.cache(name, value);
            }
        }


        function clear(name) {
            if (name) {
                service.data[name] = undefined;
                localStorage.removeItem(name);
            } else {
                for (var property in service.data) {
                    if (service.data.hasOwnProperty(property)) {
                        service.clear(property);
                    }
                }
                for (var property in localStorage) {
                    if (localStorage.hasOwnProperty(property)) {
                        service.clear(property);
                    }
                }
            }
        }

        function user() {
            return service.get('user');
        }
    }
})();