var UserService = require('./userService.js');
var ResponseUtil = require('../util/responseUtil.js');

module.exports = {
    addOne: addOne,
    deleteOne: deleteOne,
    findAll: findAll,
    findOne: findOne,
    updateOne: updateOne
};

/////////////////////////////

function addOne(req, res) {
    UserService
        .addOne(req.mtnUser, req.body)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}

function deleteOne(req, res) {
    UserService
        .deleteOne(req.mtnUser, req.params.userId)
        .then(function() {
            ResponseUtil.ok(res);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}

function findAll(req, res) {
    UserService
        .findAll(req.mtnUser, req.query)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}

function findOne(req, res) {
    UserService
        .findOne(req.mtnUser, req.params.userId)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}

function updateOne(req, res) {
    UserService
        .updateOne(req.mtnUser, req.params.userId, req.body)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}
