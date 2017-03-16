var Sequelize = require('sequelize');
var _ = require('lodash');

var Properties = require('./properties.js');

////////////////////////////////////////

module.exports = buildInstance();

////////////////////////////////////////

function buildInstance() {

    //If we have a connection string from Heroku, use it
    var herokuConnectionString = process.env.DATABASE_URL;
    if (herokuConnectionString) {
        return new Sequelize(herokuConnectionString, buildLimitedOptions());
    }
    //Else, load from local configuration
    else {
        var database = validate(getDatabase());
        var username = validate(getUsername());
        var password = validate(getPassword());

        return new Sequelize(database, username, password, buildOptions());
    }
}

function buildOptions() {
    return {
        host: validate(getHost()),
        dialect: validate(getDialect()),
        logging: validate(getShowSql()),
        pool: buildPool(),
        define: buildDefine()
    }
}

function buildLimitedOptions() {
    return {
        logging: validate(getShowSql()),
        pool: buildPool(),
        define: buildDefine()
    };
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

function getShowSql() {
    return Properties.database.showSql;
}

function buildDefine() {
    return {
        charset: 'utf8',
        createdAt: 'created_date',
        deletedAt: 'deleted_date',
        freezeTableName: true,
        paranoid: true,
        timezone: '+00:00', //UTC time
        underscored: true,
        updatedAt: 'updated_date'
    }
}