var Sequelize = require('sequelize');
var _ = require('lodash');

var Properties = require('./properties.js');
var Logger = require('./logger.js');

////////////////////////////////////////

Logger.info('Properties').json(Properties).build();

module.exports = buildInstance();

////////////////////////////////////////

function buildInstance() {
    var database = validate(getDatabase());
    var username = validate(getUsername());
    var password = validate(getPassword());

    var options = buildOptions();

    return new Sequelize(database, username, password, options);
}

function buildOptions() {
    return {
        host: validate(getHost()),
        dialect: validate(getDialect()),
        pool: buildPool()
    }
}

function buildPool() {
    return {
        max: validate(getMaxPoolSize()),
        min: validate(getMinPoolSize()),
        idle: validate(getMaxIdleTimeMs())
    }
}

/**
 * Ensures the provided value was actually loaded, and will throw an error if it is not
 */
function validate(value) {
    if (_.isNull(value) || _.isUndefined(value)) {
        throw new Error('Failed to load database property!');
    }
    return value;
}

//TODO These getters will be enhanced to use Heroku env variables if I can't just put those variables into our config files

function getDatabase() {
    return Properties.database.name;
}

function getUsername() {
    return Properties.database.username;
}

function getPassword() {
    return Properties.database.password;
}

function getHost() {
    return Properties.database.host;
}

function getDialect() {
    return Properties.database.dialect;
}

function getMaxPoolSize() {
    return Properties.database.maxPoolSize;
}

function getMinPoolSize() {
    return Properties.database.minPoolSize;
}

function getMaxIdleTimeMs() {
    return Properties.database.maxIdleTimeMs;
}