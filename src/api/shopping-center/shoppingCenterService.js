var q = require('q');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
var ShoppingCenter = Models.ShoppingCenter;

module.exports = {
    addOne: addOne,
    findAll: findAll
};

////////////////////////////

function addOne(user, request) {

}

function findAll(user) {
    return q
        .fcall(function() {
            user.authorize(User.Permission.READ_SHOPPING_CENTER);
        })
        .then(function() {
            return q(ShoppingCenter
                .findAll())
                .then(function(results) {
                    Logger.info('Retrieved Shopping Centers')
                        .user(user)
                        .build();
                    return results;
                });
        })
        .catch(function(error) {
            Logger.error('Failed to retrieve Shopping Centers')
                .user(user)
                .exception(error)
                .build();
            throw error;
        });
}