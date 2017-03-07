var express = require('express');
var Router = express.Router();

var PingController = require('./ping/pingController.js');

Router.route('/ping').get(PingController.ping);

module.exports = Router;