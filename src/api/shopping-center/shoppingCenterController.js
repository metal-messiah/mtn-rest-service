var ShoppingCenterService = require('./shoppingCenterService.js');
var Errors = require('../error/errors.js');

module.exports = {
    findAll: findAll
};

/////////////////////////////

function findAll(req, res) {
    ShoppingCenterService
        .findAll(req.mtnUser)
        .then(function(data) {
            res.status(200).json(data);
        })
        .catch(function(error) {
            //TODO create a ControllerUtil that can generically handle creating responses!
            if (error instanceof Errors.UnauthorizedError) {
                res.status(401).json({message: error.message});
            } else {
                res.status(400).json({message: error.message});
            }
        });
}