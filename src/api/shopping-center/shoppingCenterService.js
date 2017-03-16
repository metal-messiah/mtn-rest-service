var q = require('q');

var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
var ShoppingCenter = Models.ShoppingCenter;

module.exports = {
    findAll: findAll
};

////////////////////////////

function findAll(req) {
    return q(ShoppingCenter
        .findAll())
        .then(function(results) {
            Logger.info('Retrieved Shopping Centers')
                .user(req.mtnUser)
                .build();
            return results;
        })
        .catch(function(error) {
            Logger.error('Failed to retrieve Shopping Centers')
                .user(req.mtnUser)
                .exception(error)
                .build();
            throw error;
        });
}