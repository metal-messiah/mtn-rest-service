var auth0 = require('auth0-js');
var q = require('q');

var Properties = require('../util/properties.js');
var Logger = require('../util/logger.js');

module.exports = AuthService();

/**
 * This service is simply a convenience to wrap the Auth0 client
 * and provide a proper promise response.
 */
function AuthService() {
    var service = {
        client: new auth0.Authentication({
            domain: Properties.auth.domain,
            clientID: Properties.auth.clientId
        }),
        getUserProfile: getUserProfile
    };

    return service;

    ///////////////////////////////

    function getUserProfile(accessToken) {
        var deferred = q.defer();

        service.client.userInfo(accessToken, function(error, profile) {
            if (error) {
                Logger.error('Failed to retrieve user profile')
                    .keyValue('accessToken', accessToken)
                    .exception(error)
                    .build();

                deferred.reject(error);
            } else {
                Logger.info('Successfully retrieved user profile')
                    .keyValue('email', profile.email)
                    .build();
                deferred.resolve(profile);
            }
        });

        return deferred.promise;
    }
}