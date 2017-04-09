var q = require('q');
var _ = require('lodash');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/database.js');
var ShoppingCenter = Models.ShoppingCenter;
var ShoppingCenterAccess = Models.ShoppingCenterAccess;
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
        /*
        The Sequelize ValidationError thrown when the value doesn't match the specified values
        is insufficient to communicate anything meaningful, so we have to validate this ourselves
         */
        if (!_.includes(ShoppingCenterAccess.attributes.accessType.values, request.accessType)) {
            throw new Errors.BadRequestError('accessType must be one of: ' + ShoppingCenterAccess.attributes.accessType.values.join(', '));
        }
        //TODO check permission
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

function deleteAllForShoppingCenter(user, shoppingCenterId) {
    return q
        .fcall(validate)
        .then(deleteShoppingCenterAccesses)
        .catch(handleError);

    ///////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(shoppingCenterId)) {
            throw new Errors.BadRequestError('Invalid Shopping Center Access ID Provided');
        }
        //TODO check permission
    }

    function deleteShoppingCenterAccesses() {
        return q(ShoppingCenterAccess
            .destroy({
                where: {
                    shoppingCenterId: shoppingCenterId
                }
            }))
            .then(function(rows) {
                Logger.info('Deleted Accesses for Shopping Center')
                    .user(user)
                    .keyValue('shoppingCenterId', shoppingCenterId)
                    .keyValue('count', rows)
                    .build();
            });
    }

    function handleError(error) {
        Logger.error('Failed to delete Accesses for Shopping Center')
            .user(user)
            .keyValue('shoppingCenterId', shoppingCenterId)
            .exception(error)
            .build();
        throw error;
    }
}

function deleteOne(user, id) {
    return q
        .fcall(validate)
        .then(deleteShoppingCenterAccess)
        .catch(handleError);

    ///////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center Access ID Provided');
        }
        //TODO check permission
    }

    function deleteShoppingCenterAccess() {
        return q(ShoppingCenterAccess
            .destroy({
                where: {
                    id: id
                }
            }))
            .then(function(rows) {
                Logger.info('Deleted Shopping Center Access')
                    .user(user)
                    .keyValue('shoppingCenterAccessId', id)
                    .keyValue('count', rows)
                    .build();
            });
    }

    function handleError(error) {
        Logger.error('Failed to delete Shopping Center Access')
            .user(user)
            .keyValue('shoppingCenterAccessId', id)
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
        //TODO check permission
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

function findOne(user, id) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenterAccess)
        .catch(handleError);

    //////////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center Access ID Provided');
        }
        //TODO check permission
    }

    function retrieveShoppingCenterAccess() {
        return q(ShoppingCenterAccess
            .findById(id))
            .then(function(result) {
                Logger.info('Retrieved Shopping Center Access')
                    .user(user)
                    .keyValue('shoppingCenterAccessId', id)
                    .build();
                return result;
            });
    }

    function handleError(error) {
        Logger.error('Failed to retrieve Shopping Center Access')
            .user(user)
            .keyValue('shoppingCenterAccessId', id)
            .exception(error)
            .build();
        throw error;
    }
}

function updateOne(user, id, request) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenterAccess)
        .then(enforceVersion)
        .then(updateShoppingCenterAccess)
        .catch(handleError);

    /////////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center Access ID Provided');
        }
        if (!_.includes(ShoppingCenterAccess.attributes.accessType.values, request.accessType)) {
            throw new Errors.BadRequestError('accessType must be one of: ' + ShoppingCenterAccess.attributes.accessType.values.join(', '));
        }
        if (!request.version) {
            throw new Errors.BadRequestError('No Version Provided');
        }
        //TODO check permission
    }

    function retrieveShoppingCenterAccess() {
        return q(ShoppingCenterAccess
            .findById(id))
            .then(function(result) {
                if (!result) {
                    throw new Errors.BadRequestError('Shopping Center Access not found');
                }
                return result;
            });
    }

    function enforceVersion(existing) {
        //Return a 409 Conflict if request data is out-of-date
        if (existing.version !== request.version) {
            throw new Errors.ConflictError(existing);
        }
    }

    function updateShoppingCenterAccess() {
        var options = {
            fields: ['accessType', 'hasLeftIn', 'hasLeftOut', 'hasRightIn', 'hasRightOut', 'hasTrafficLight', 'isOneWayRoad', 'shoppingCenterId'],
            returning: true,
            where: {
                id: id
            }
        };

        request.version++;

        return q(ShoppingCenterAccess
            .update(request, options))
            .then(function(results) {
                Logger.info('Updated Shopping Center Access')
                    .user(user)
                    .keyValue('shoppingCenterAccessId', id)
                    .json(request)
                    .build();
                return results[1][0];
            })
            .catch(Utils.handleSequelizeException);
    }

    function handleError(error) {
        Logger.error('Failed to update Shopping Center Access')
            .user(user)
            .keyValue('shoppingCenterAccessId', id)
            .json(request)
            .exception(error)
            .build();
        throw error;
    }
}