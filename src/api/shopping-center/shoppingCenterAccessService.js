var q = require('q');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
var ShoppingCenter = Models.ShoppingCenter;
var ShoppingCenterAccess = Models.ShoppingCenterAccess;
var Utils = require('../util/utils.js');
var Errors = require('../error/errors.js');

module.exports = {
    addOne: addOne,
    findAllForShoppingCenter: findAllForShoppingCenter
};

/////////////////////////

function addOne(user, shoppingCenterId, request) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenter)
        .then(createShoppingCenterAccess)
        .catch(handleError);

    //////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(shoppingCenterId)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        user.authorize(User.Permission.EDIT_SHOPPING_CENTER);
    }

    function retrieveShoppingCenter() {
        return q(ShoppingCenter
            .findById(shoppingCenterId)
            .then(function(result) {
                if (!result) {
                    throw new Errors.BadRequestError('Shopping Center not found');
                }
                return result;
            }));
    }

    function createShoppingCenterAccess(shoppingCenter) {
        var options = {
            fields: ['accessType', 'hasLeftIn', 'hasLeftOut', 'hasRightIn', 'hasRightOut', 'hasTrafficLight', 'isOneWayRoad', 'shoppingCenterId'],
            returning: true
        };

        return q(shoppingCenter
            .createShoppingCenterAccess(request, options))
            .then(function(result) {
                Logger.info('Created Access for Shopping Center')
                    .user(user)
                    .keyValue('shoppingCenterId', shoppingCenterId)
                    .json(request)
                    .build();
                return result;
            })
            .catch(Utils.handleSequelizeException);
    }

    function handleError(error) {
        Logger.error('Failed to create Access for Shopping Center')
            .user(user)
            .keyValue('shoppingCenterId', shoppingCenterId)
            .json(request)
            .exception(error)
            .build();
        throw error;
    }
}

function findAllForShoppingCenter(user, shoppingCenterId) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenterAccesses)
        .catch(handleError);

    //////////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(shoppingCenterId)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        user.authorize(User.Permission.READ_SHOPPING_CENTER);
    }

    function retrieveShoppingCenterAccesses() {
        var options = {
            order: 'id ASC',
            where: {
                shoppingCenterId: shoppingCenterId
            }
        };

        return q(ShoppingCenterAccess
            .findAll(options))
            .then(function(results) {
                Logger.info('Retrieved Shopping Center Accesses')
                    .user(user)
                    .keyValue('shoppingCenterId', shoppingCenterId)
                    .build();
                return results;
            });
    }

    function handleError(error) {
        Logger.error('Failed to retrieve Shopping Center Accesses')
            .user(user)
            .keyValue('shoppingCenterId', shoppingCenterId)
            .exception(error)
            .build();
        throw error;
    }
}