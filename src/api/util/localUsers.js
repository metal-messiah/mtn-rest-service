var _ = require('lodash');

var Properties = require('./properties.js');

/**
 * A simple service for loading users from configuration,
 * for use with Basic Auth middleware.
 */
var service = {
    checkUser: checkUser
};

module.exports = service;

function checkUser(username, password) {
    var user = _.find(Properties.users, function(user) {
        return user.username.toLowerCase() === username.toLowerCase()
            && user.password === password;
    });

    if (user) {
        return user;
    } else {
        new Error('Invalid Username or Password');
    }
}