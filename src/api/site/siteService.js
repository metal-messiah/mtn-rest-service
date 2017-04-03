var q = require('q');
var _ = require('lodash');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
var ShoppingCenter = Models.ShoppingCenter;
var Site = Models.Site;
var Utils = require('../util/utils.js');
var Errors = require('../error/errors.js');

module.exports = {
    addOne: addOne,
    deleteAllForShoppingCenter: deleteAllForShoppingCenter,
    deleteOne: deleteOne,
    findAllForShoppingCenter: findAllForShoppingCenter,
    findOne: findOne,
    updateOne: updateOne
};

//////////////////////////////

function addOne(user, shoppingCenterId, request) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenter)
        .then(createSite)
        .catch(handleError);

    /////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(shoppingCenterId)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        if (!_.includes(Site.attributes.type.values, request.locationType)) {
            throw new Errors.BadRequestError('type must be one of: ' + Site.attributes.type.values.join(', '));
        }
        if (!_.includes(Site.attributes.locationType.values, request.locationType)) {
            throw new Errors.BadRequestError('locationType must be one of: ' + Site.attributes.locationType.values.join(', '));
        }
        user.authorize(User.Permission.CREATE_SITE);
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

    function createSite(shoppingCenter) {
        var options = {
            fields: [],
            returning: true
        };

        return q(shoppingCenter
            .createSite(request, options))
            .then(function(result) {
                Logger.info('Created Site for Shopping Center')
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

function deleteAllForShoppingCenter(user, shoppingCenterId) {
    return q
        .fcall(validate)
        .then(deleteSites)
        .catch(handleError);

    //////////////////////////

    //TODO pick up here
}

function deleteOne(user, id) {
    return q
        .fcall(validate)
        .then(deleteSite)
        .catch(handleError);

    //////////////////////////

}

function findAllForShoppingCenter(user, shoppingCenterId) {
    return q
        .fcall(validate)
        .then(retrieveSites)
        .catch(handleError);

    //////////////////////////

}

function findOne(user, id) {
    return q
        .fcall(validate)
        .then(retrieveSite)
        .catch(handleError);

    //////////////////////////

}

function updateOne(user, id, request) {
    return q
        .fcall(validate)
        .then(retrieveSite)
        .then(enforceVersion)
        .then(updateSite)
        .catch(handleError);

    //////////////////////////

}