var ShoppingCenterService = require('./shoppingCenterService.js');
var ResponseUtil = require('../util/responseUtil.js');

module.exports = {
    addOne: addOne,
    findAll: findAll,
    findOne: findOne
};

/////////////////////////////

function addOne(req, res) {
    ShoppingCenterService
        .addOne(req.mtnUser, req.body)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}

function findAll(req, res) {
    ShoppingCenterService
        .findAll(req.mtnUser, req.query)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}

function findOne(req, res) {
    ShoppingCenterService
        .findOne(req.mtnUser, req.params.shoppingCenterId)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}