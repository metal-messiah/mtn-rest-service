var express = require('express');
var Router = express.Router();

var PingController = require('./ping/pingController.js');
var ShoppingCenterController = require('./shopping-center/shoppingCenterController.js');

Router.route('/ping')
    .get(PingController.ping);

Router.route('/shopping-center')
    .get(ShoppingCenterController.findAll)
    .post(ShoppingCenterController.addOne);

Router.route('/shopping-center/:shoppingCenterId')
    .get(ShoppingCenterController.findOne)
    .delete(ShoppingCenterController.deleteOne)
    .put(ShoppingCenterController.updateOne);

module.exports = Router;