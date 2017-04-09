var q = require('q');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/database.js');
var ShoppingCenter = Models.ShoppingCenter;
var Utils = require('../util/utils.js');
var Errors = require('../error/errors.js');

module.exports = {
    addOne: addOne,
    deleteOne: deleteOne,
    findAll: findAll,
    findOne: findOne,
    updateOne: updateOne
};

////////////////////////////

function addOne(user, request) {
    return q
        .fcall(validate)
        .then(createShoppingCenter)
        .catch(handleError);

    //////////////////////////////

    function validate() {
        //TODO check permission
    }

    function createShoppingCenter() {
        var options = {
            fields: ['name', 'nativeId', 'owner', 'url'],
            returning: true
        };

        return q(ShoppingCenter
            .create(request, options))
            .then(function(result) {
                Logger.info('Created Shopping Center')
                    .user(user)
                    .json(request)
                    .build();
                return result;
            })
            .catch(Utils.handleSequelizeException);
    }

    function handleError(error) {
        Logger.error('Failed to create Shopping Center')
            .user(user)
            .json(request)
            .exception(error)
            .build();
        throw error;
    }
}

function deleteOne(user, id) {
    return q
        .fcall(validate)
        .then(deleteShoppingCenter)
        .catch(handleError);

    ///////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        //TODO check permission
    }

    function deleteShoppingCenter() {
        return q(ShoppingCenter
            .destroy({
                where: {
                    id: id
                }
            }))
            .then(function(rows) {
                Logger.info('Deleted Shopping Center')
                    .user(user)
                    .keyValue('shoppingCenterId', id)
                    .keyValue('count', rows)
                    .build();
            });
    }

    function handleError(error) {
        Logger.error('Failed to delete Shopping Center')
            .user(user)
            .keyValue('shoppingCenterId', id)
            .exception(error)
            .build();
        throw error;
    }
}

function findAll(user, params) {
    var options = initFindAllOptions(params);

    return q
        .fcall(validate)
        .then(retrieveShoppingCenters)
        .catch(handleError);

    ///////////////////////

    function validate() {
        //TODO check permission
    }

    function retrieveShoppingCenters() {
        var promises = [];

        //Pagination count
        promises.push(
            q(ShoppingCenter
                .count(options)
                .then(function(count) {
                    return count;
                }))
        );

        //Actual query
        promises.push(
            q(ShoppingCenter
                .findAll(options))
                .then(function(results) {
                    Logger.info('Retrieved Shopping Centers')
                        .user(user)
                        .build();
                    return results;
                })
        );

        return q
            .all(promises)
            .then(function(promiseResults) {
                return new Utils.Pagination(promiseResults[1], promiseResults[0], options);
            })
    }

    function handleError(error) {
        Logger.error('Failed to retrieve Shopping Centers')
            .user(user)
            .exception(error)
            .build();
        throw error;
    }
}

function findOne(user, id) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenter)
        .catch(handleError);

    /////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        //TODO check permission
    }

    function retrieveShoppingCenter() {
        return q(ShoppingCenter
            .findById(id)
            .then(function(result) {
                Logger.info('Retrieved Shopping Center')
                    .user(user)
                    .keyValue('shoppingCenterId', id)
                    .build();
                return result;
            }));
    }

    function handleError(error) {
        Logger.error('Failed to retrieve Shopping Center')
            .user(user)
            .keyValue('shoppingCenterId', id)
            .exception(error)
            .build();
        throw error;
    }
}

function updateOne(user, id, request) {
    return q
        .fcall(validate)
        .then(retrieveShoppingCenter)
        .then(enforceVersion)
        .then(updateShoppingCenter)
        .catch(handleError);

    /////////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        if (!request.version) {
            throw new Errors.BadRequestError('No Version Provided');
        }
        //TODO check permission
    }

    function retrieveShoppingCenter() {
        return q(ShoppingCenter
            .findById(id))
            .then(function(result) {
                if (!result) {
                    throw new Errors.BadRequestError('Shopping Center not found');
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

    function updateShoppingCenter() {
        var options = {
            fields: ['name', 'nativeId', 'owner', 'url', 'version'],
            returning: true,
            where: {
                id: id
            }
        };

        //TODO figure out why hooks aren't registering?
        request.version++;

        return q(ShoppingCenter
            .update(request, options))
            .then(function(results) {
                Logger.info('Updated Shopping Center')
                    .user(user)
                    .json(request)
                    .build();
                return results[1][0];
            })
            .catch(Utils.handleSequelizeException);
    }

    function handleError(error) {
        Logger.error('Failed to update Shopping Center')
            .user(user)
            .keyValue('shoppingCenterId', id)
            .json(request)
            .exception(error)
            .build();
        throw error;
    }
}

///////////////////////////////

function initFindAllOptions(params) {
    var options = {
        limit: 10,
        offset: 0,
        order: 'id DESC',
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