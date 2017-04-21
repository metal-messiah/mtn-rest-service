var NodeCache = require('node-cache');

module.exports = Cache();

function Cache() {
    var service = {
        caches: {
            userCache: new NodeCache({stdTTL: (60 * 60), checkperiod: (60 * 5)})
        },
        systemAdministrator: systemAdministrator,
        user: user
    };

    return service;

    ///////////////////////////////

    function systemAdministrator(value) {
        if (typeof value !== 'undefined') {
            return service.caches.userCache.set('system_administrator', value);
        } else {
            return service.caches.userCache.get('system_administrator');
        }
    }

    function user(id, value) {
        if (typeof value !== 'undefined') {
            return service.caches.userCache.set(id, value);
        } else {
            return service.caches.userCache.get(id);
        }
    }
}