var q = require('q');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
var ShoppingCenter = Models.ShoppingCenter;
var Utils = require('../util/utils.js');

module.exports = {
    addOne: addOne,
    findAll: findAll
};

////////////////////////////

function addOne(user, request) {

}

function findAll(user, params) {
    var where = initFindAllOptions(params);

    return q
        .fcall(function() {
            user.authorize(User.Permission.READ_SHOPPING_CENTER);
        })
        .then(function() {
            return q(ShoppingCenter
                .findAll(where))
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

function initFindAllOptions(params) {
    var options = {
        limit: 10,
        offset: 0,
        where: {}
    };

    //Handle pagination
    if (Utils.isPositiveInteger(params.limit)) {
        options.limit = Number(params.limit);
    }
    if (Utils.isPositiveInteger(params.offset)) {
        options.offset = Number(params.offset);
    }

    if (typeof params.name !== 'undefined') {
        options.where.name = {
            $iLike: params.name + '%'
        };
    }
    if (typeof params.nativeId !== 'undefined') {
        options.where.nativeId = params.nativeId;
    }
    if (typeof params.owner !== 'undefined') {
        options.where.owner = {
            $iLike: params.owner + '%'
        };
    }

    return options;
}