var q = require('q');

var User = require('../auth/user.js');
var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
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
        .fcall(function() {
            user.authorize(User.Permission.CREATE_SHOPPING_CENTER);
        })
        .then(function() {
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
        })
        .catch(function(error) {
            Logger.error('Failed to create Shopping Center')
                .user(user)
                .json(request)
                .exception(error)
                .build();
            throw error;
        });
}

function deleteOne(user, id) {
    return q
        .fcall(function() {
            if (!Utils.isPositiveInteger(id)) {
                throw new Errors.BadRequestError('Invalid ID Provided');
            }
            user.authorize(User.Permission.DELETE_SHOPPING_CENTER);
        })
        .then(function() {
            return q(ShoppingCenter
                .destroy({
                    where: {
                        id: id
                    }
                }))
                .then(function(rows) {
                    Logger.info('Deleted Shopping Center')
                        .user(user)
                        .keyValue('id', id)
                        .keyValue('count', rows)
                        .build();
                });
        })
        .catch(function(error) {
            Logger.error('Failed to delete Shopping Center')
                .user(user)
                .exception(error)
                .build();
            throw error;
        });
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

function findOne(user, id) {
    return q
        .fcall(function() {
            if (!Utils.isPositiveInteger(id)) {
                throw new Errors.BadRequestError('Invalid ID Provided');
            }
            user.authorize(User.Permission.READ_SHOPPING_CENTER);
        })
        .then(function() {
            return q(ShoppingCenter
                .findById(id)
                .then(function(result) {
                    Logger.info('Retrieved Shopping Center')
                        .user(user)
                        .keyValue('id', id)
                        .build();
                    return result;
                }));
        })
        .catch(function(error) {
            Logger.error('Failed to retrieve Shopping Center')
                .user(user)
                .keyValue('id', id)
                .exception(error)
                .build();
            throw error;
        });
}

function updateOne(user, id, request) {
    return q
        .fcall(function() {
            if (!Utils.isPositiveInteger(id)) {
                throw new Errors.BadRequestError('Invalid ID Provided');
            }
            if (!request.version) {
                throw new Errors.BadRequestError('No Version Provided');
            }
            user.authorize(User.Permission.EDIT_SHOPPING_CENTER);
        })
        .then(function() {
            return q(ShoppingCenter
                .findById(id)
                .then(function(result) {
                    if (!result) {
                        throw new Errors.BadRequestError('Shopping Center not found');
                    }
                    return result;
                }));
        })
        .then(function(existing) {
            //Return a 409 Conflict if request data is out-of-date
            if (existing.version !== request.version) {
                throw new Errors.ConflictError(existing);
            }
        })
        .then(function() {
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
        })
        .catch(function(error) {
            Logger.error('Failed to update Shopping Center')
                .user(user)
                .keyValue('id', id)
                .json(request)
                .exception(error)
                .build();
            throw error;
        });
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