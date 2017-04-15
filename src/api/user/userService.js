var q = require('q');

var Logger = require('../util/logger.js');
var Models = require('../model/database.js');
var UserProfile = Models.UserProfile;
var UserIdentity = Models.UserIdentity;
var Utils = require('../util/utils.js');
var Errors = require('../error/errors.js');

module.exports = {
    addOne: addOne,
    deleteOne: deleteOne,
    findAll: findAll,
    findOne: findOne,
    updateOne: updateOne
};

///////////////////////////////

function addOne(user, request) {
    return q
        .fcall(validate)
        .then(createUser)
        .catch(handleError);

    //////////////////////////////

    function validate() {
        //TODO check permission
    }

    function createUser() {
        var options = {
            fields: ['email', 'first_name', 'last_name'],
            returning: true
        };

        return q(UserProfile
            .create(request, options))
            .then(function(result) {
                Logger.info('Created User')
                    .user(user)
                    .json(request)
                    .build();
                return result;
            })
            .catch(Utils.handleSequelizeException);
    }

    function handleError(error) {
        Logger.error('Failed to create User')
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
        .then(deleteUser)
        .catch(handleError);

    ///////////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid User ID Provided');
        }
        //TODO check permission
    }

    function deleteUser() {
        return q(UserProfile
            .destroy({
                where: {
                    id: id
                }
            }))
            .then(function(rows) {
                Logger.info('Deleted User')
                    .user(user)
                    .keyValue('userId', id)
                    .keyValue('count', rows)
                    .build();
            });
    }

    function handleError(error) {
        Logger.error('Failed to delete User')
            .user(user)
            .keyValue('userId', id)
            .exception(error)
            .build();
        throw error;
    }
}

function findAll(user, params) {
    var options = initFindAllOptions(params);

    return q
        .fcall(validate)
        .then(retrieveUsers)
        .catch(handleError);

    ////////////////////////////////

    function validate() {
        //TODO check permission
    }

    function retrieveUsers() {
        var promises = [];

        //Pagination count
        promises.push(
            q(UserProfile
                .count(options))
        );

        //Actual query
        promises.push(
            q(UserProfile
                .findAll(options))
                .then(function(results) {
                    Logger.info('Retrieved Users')
                        .user(user)
                        .build();
                    return results;
                })
        );

        return q
            .all(promises)
            .then(function(promiseResults) {
                return new Utils.Pagination(promiseResults[1], promiseResults[0], options);
            });
    }

    function handleError(error) {
        Logger.error('Failed to retrieve Users')
            .user(user)
            .exception(error)
            .build();
        throw error;
    }
}

function findOne(user, id) {
    return q
        .fcall(validate)
        .then(retrieveUser)
        .catch(handleError);

    //////////////////////////////

    function validate() {
        //TODO check permission
    }

    function retrieveUser() {
        var options;

        if (Utils.isPositiveInteger(id)) {
            options = {
                include: [{
                    model: UserIdentity,
                    separate: true
                }],
                where: {
                    id: id
                }
            }
        } else {
            options = {
                include: [{
                    model: UserIdentity,
                    separate: true,
                    through: {
                        where: {
                            provider_user_id: id
                        }
                    }
                }]
            }
        }

        return q(UserProfile
            .unscoped()
            .findOne(options))
            .then(function(result) {
                Logger.info('Retrieved User')
                    .user(user)
                    .keyValue('userId', id)
                    .build();
                return result;
            });
    }

    function handleError(error) {
        Logger.error('Failed to retrieve User')
            .user(user)
            .keyValue('userId', id)
            .exception(error)
            .build();
        throw error;
    }
}

function updateOne(user, id, request) {
    return q
        .fcall(validate)
        .then(retrieveUser)
        .then(updateUser)
        .catch(handleError);

    /////////////////////////////

    function validate() {
        if (!Utils.isPositiveInteger(id)) {
            throw new Errors.BadRequestError('Invalid Shopping Center ID Provided');
        }
        //TODO check permission
    }

    function retrieveUser() {
        return q(UserProfile
            .findById(id))
            .then(function(result) {
                if (!result) {
                    throw new Errors.BadRequestError('User not found');
                }
                return result;
            });
    }

    function updateUser(existing) {
        if (request.email) {
            existing.email = request.email;
        }
        existing.first_name = request.first_name;
        existing.last_name = request.last_name;

        return q(existing
            .save())
            .then(function(result) {
                Logger.info('Updated User Profile')
                    .user(user)
                    .keyValue('userId', id)
                    .json(request)
                    .build();
                return result;
            })
            .catch(Utils.handleSequelizeException);
    }

    function handleError(error) {
        Logger.error('Failed to update User Profile')
            .user(user)
            .keyValue('userId', id)
            .json(request)
            .exception(error)
            .build();
        throw error;
    }
}

////////////////////////////////////

function initFindAllOptions(params) {
    var options = {
        limit: 10,
        offset: 0,
        order: 'id ASC',
        where: {}
    };

    //Handle pagination
    if (Utils.isPositiveInteger(params.limit)) {
        options.limit = Number(params.limit);
    }
    if (Utils.isPositiveInteger(params.offset)) {
        options.offset = Number(params.offset);
    }

    if (typeof params.q !== 'undefined') {
        options.where = {
            $or: [
                {
                    email: {
                        $iLike: params.q + '%'
                    }
                },
                {
                    first_name: {
                        $iLike: params.q + '%'
                    }
                },
                {
                    last_name: {
                        $iLike: params.q + '%'
                    }
                }
            ]
        }
    } else {
        if (typeof params.email !== 'undefined') {
            options.where.email = {
                $iLike: params.email + '%'
            };
        }
        if (typeof params.firstName !== 'undefined') {
            options.where.first_name = {
                $iLike: params.firstName + '%'
            };
        }
        if (typeof params.lastName !== 'undefined') {
            options.where.last_name = {
                $iLike: params.lastName + '%'
            };
        }
    }

    return options;
}