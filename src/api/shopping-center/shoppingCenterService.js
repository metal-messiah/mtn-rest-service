var q = require('q');

var Logger = require('../util/logger.js');
var Models = require('../model/models.js');
var ShoppingCenter = Models.ShoppingCenter;

module.exports = {
    findAll: findAll
};

////////////////////////////

function findAll() {
    return q(ShoppingCenter
        .findAll())
        .then(function(results) {
            Logger.info('Retrieved Shopping Centers')
                .build();
            return results;
        })
        .catch(function(error) {
            Logger.error('Failed to retrieve Shopping Centers')
                .exception(error)
                .build();
            throw error;
        });
}