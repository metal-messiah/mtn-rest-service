var express = require('express');
var Router = express.Router();

var PingController = require('./ping/pingController.js');
// var ShoppingCenterController = require('./shopping-center/shoppingCenterController.js');
var UserController = require('./user/userController.js');

Router.route('/ping')
    .get(PingController.ping);

// Router.route('/shopping-center')
//     .get(ShoppingCenterController.findAll)
//     .post(ShoppingCenterController.addOne);
//
// Router.route('/shopping-center/:shoppingCenterId')
//     .get(ShoppingCenterController.findOne)
//     .delete(ShoppingCenterController.deleteOne)
//     .put(ShoppingCenterController.updateOne);
//
// Router.route('/shopping-center/:shoppingCenterId/access')
//     .get(ShoppingCenterController.findAllAccesses)
//     .post(ShoppingCenterController.addOneAccess)
//     .delete(ShoppingCenterController.deleteAllAccesses);
//
// Router.route('/shopping-center/access/:shoppingCenterAccessId')
//     .get(ShoppingCenterController.findOneAccess)
//     .delete(ShoppingCenterController.deleteOneAccess)
//     .put(ShoppingCenterController.updateOneAccess);

Router.route('/user')
    .get(UserController.findAll)
    .post(UserController.addOne);

Router.route('/user/:userId')
    .get(UserController.findOne)
    .put(UserController.updateOne)
    .delete(UserController.deleteOne);

module.exports = Router;