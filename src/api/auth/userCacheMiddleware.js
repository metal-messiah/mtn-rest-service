var auth0 = require('auth0-js');
var q = require('q');

var Properties = require('../util/properties.js');
var Cache = require('../util/cache.js');
var Logger = require('../util/logger.js');
var User = require('./user.js');

var Auth0 = new auth0.Authentication({
    domain: Properties.auth.domain,
    clientID: Properties.auth.clientId
});

module.exports = UserCacheMiddleware;

function UserCacheMiddleware(req, res, next) {
    var user;

    q.fcall(validateRequestUserPresent)
        .then(loadUser)
        .then(appendUserToRequest)
        .then(next)
        .catch(function(error) {
            Logger.warn('Failed to handle caching user')
                .exception(error)
                .build();
            return res.status(401).send('Unauthorized');
        });

    ////////////////////

    function validateRequestUserPresent() {
        if (!req.user || !req.user.sub) {
            throw new Error('No user found on request');
        }
    }

    function loadUser() {
        var deferred = q.defer();

        user = getUserFromCache(req);
        if (user) {
            deferred.resolve();
        } else {
            var accessToken = req.headers['mtn-access-token'];
            Auth0.userInfo(accessToken, function(error, profile) {
                if (error) {
                    deferred.reject(error);
                } else {
                    //User email must be verified
                    if (!profile['email_verified']) {
                        deferred.reject(new Error('Email not verified'));
                    }

                    //Build and return user
                    try {
                        user = User.build(profile);
                        Cache.user(user.id, user);
                        deferred.resolve();
                    } catch(e) {
                        deferred.reject(e);
                    }
                }
            });
        }

        return deferred.promise;
    }

    function appendUserToRequest() {
        req.mtnUser = user;
    }
}

function getUserFromCache(req) {
    var userId = getUserId(req);
    return Cache.user(userId);
}


function getUserId(req) {
    var sub = req.user.sub;
    return sub.substring(sub.indexOf('|') + 1, sub.length);
}