var NodeCache = require('node-cache');

module.exports = Cache();

function Cache() {
    var service = {
        caches: {
            userCache: new NodeCache({stdTTL: (60 * 60), checkperiod: (60 * 5)})
        },
        user: user
    };

    return service;

    ///////////////////////////////

    function user(id, value) {
        if (typeof value !== 'undefined') {
            return service.caches.userCache.set(id, value);
        } else {
            return service.caches.userCache.get(id);
        }
    }
}