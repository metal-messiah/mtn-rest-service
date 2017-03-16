var q = require('q');

var Cache = require('../util/cache.js');
var Logger = require('../util/logger.js');
var User = require('./user.js');
var AuthService = require('./authService.js');

module.exports = UserCacheMiddleware;

function UserCacheMiddleware(req, res, next) {
    var user;

    return q.fcall(validateRequestUserPresent)
        .then(loadUserFromCache)
        .then(loadUserFromProvider)
        .then(appendUserToRequest)
        .then(next)
        .catch(sendUnauthorizedResponse);

    ////////////////////

    function validateRequestUserPresent() {
        if (!req.user || !req.user.sub) {
            throw new Error('No user found on request');
        }
    }

    function loadUserFromCache() {
        var userId = getUserId(req);
        user = Cache.user(userId);
    }

    function loadUserFromProvider() {
        if (!user) {
            var accessToken = req.headers['mtn-access-token'];
            return AuthService.getUserProfile(accessToken)
                .then(function(profile) {
                    user = User.build(profile);
                    Cache.user(user.id, user);
                });
        }
    }

    function appendUserToRequest() {
        req.mtnUser = user;
    }

    function getUserId(req) {
        var sub = req.user.sub;
        return sub.substring(sub.indexOf('|') + 1, sub.length);
    }

    function sendUnauthorizedResponse(error) {
        Logger.warn('Failed to handle caching user')
            .exception(error)
            .build();
        return res.status(401).send('Unauthorized');
    }
}