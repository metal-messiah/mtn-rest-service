var ShoppingCenterService = require('./shoppingCenterService.js');
var ResponseUtil = require('../util/responseUtil.js');

module.exports = {
    findAll: findAll,
    findOne: findOne
};

/////////////////////////////

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