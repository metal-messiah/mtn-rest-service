var q = require('q');

var Cache = require('../util/cache.js');
var Logger = require('../util/logger.js');
var User = require('./user.js');
var AuthToken = require('./authToken.js');
var Models = require('../model/database.js');
var UserProfile = Models.UserProfile;
var UserIdentity = Models.UserIdentity;
var AuthService = require('./authService.js');

module.exports = UserCacheMiddleware;

function UserCacheMiddleware(req, res, next) {
    var user;

    return q.fcall(validateRequestUserPresent)
        .then(loadUserFromCache)
        .then(loadUserFromDatabase)
        .then(loadUserFromProvider)
        .then(appendUserToRequest)
        .then(next)
        .catch(sendUnauthorizedResponse);

    ////////////////////

    function validateRequestUserPresent() {
        if (!req.user || !(req.user instanceof AuthToken)) {
            throw new Error('No user found on request');
        }
    }

    function loadUserFromCache() {
        user = Cache.user(req.user.getProviderUserId());
    }

    function loadUserFromDatabase() {
        var options = {
            include: [{
                model: UserIdentity,
                where: {
                    provider_user_id: req.user.getProviderUserId()
                }
            }]
        };

        return q(UserProfile
            .unscoped()
            .findAll(options))
            .then(function(result) {
                if (result) {
                    user = result;
                    Cache.user(req.user.getProviderUserId(), user);
                }
            });
    }

    function loadUserFromProvider() {
        if (!user) {
            var accessToken = req.headers['mtn-access-token'];
            return AuthService.getUserProfile(accessToken)
                .then(function(profile) {
                    var options = {
                        where: {
                            email: profile.email
                        }
                    };

                    //Now, find user in database, if any
                    return q(UserProfile
                        .unscoped()
                        .findOne(options))
                        .then(function(result) {
                            if (result) {
                                return result;
                            } else {
                                var newUser = UserProfile.build({
                                    email: profile.email
                                });
                                newUser.setCreatedBy(Cache.systemAdministrator());
                                newUser.setUpdatedBy(Cache.systemAdministrator());

                                return q(newUser
                                    .save())
                                    .then(function(result) {
                                        Cache.user(req.user.getProviderUserId(), result);
                                        user = result;
                                    })
                            }
                        })
                        .then(function(databaseUser) {

                        });
                });
        }
    }

    function appendUserToRequest() {
        req.mtnUser = user;
    }

    function sendUnauthorizedResponse(error) {
        Logger.warn('Failed to handle caching user')
            .exception(error)
            .build();
        return res.status(401).send('Unauthorized');
    }
}