var ShoppingCenterService = require('./shoppingCenterService.js');

module.exports = {
    findAll: findAll
};

/////////////////////////////

function findAll(req, res) {
    ShoppingCenterService
        .findAll(req)
        .then(function(data) {
            res.status(200).json(data);
        })
        .catch(function(error) {
            res.status(400).json({message: error.message});
        });
}