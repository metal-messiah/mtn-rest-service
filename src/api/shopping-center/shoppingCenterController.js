var ShoppingCenterService = require('./shoppingCenterService.js');
var ResponseUtil = require('../util/responseUtil.js');

module.exports = {
    findAll: findAll
};

/////////////////////////////

function findAll(req, res) {
    ShoppingCenterService
        .findAll(req.mtnUser)
        .then(function(data) {
            ResponseUtil.ok(res, data);
        })
        .catch(function(error) {
            ResponseUtil.error(res, error);
        });
}