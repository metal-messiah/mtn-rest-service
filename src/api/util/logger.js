var winston = require('winston');
var moment = require('moment');
var User = require('../auth/user.js');

var Level = {
    DEBUG: 'DEBUG',
    INFO: 'INFO',
    WARN: 'WARN',
    ERROR: 'ERROR'
};

/**
 * A custom logger to help enforce consistent logging practices,
 * and to apply some helpful formatting of timestamp and level.
 *
 * Built using the builder pattern, returning an instance of
 * LogEntry, and is used like this:
 *
 * Logger.error('Failed to save Casing')
 *       .keyValue('casingId', casingId)
 *       .exception(new Error('Something went wrong'))
 *       .json(request)
 *       .build();
 *
 * All functions are chainable, and all are optional, except build().
 *
 * No log entry will be made until build() is called.
 */
var Logger = {
    debug: debug,
    info: info,
    warn: warn,
    error: error
};

module.exports = Logger;

//////////////////////////////

function debug(message) {
    return new LogEntry(Level.DEBUG, message);
}

function info(message) {
    return new LogEntry(Level.INFO, message);
}

function warn(message) {
    return new LogEntry(Level.WARN, message);
}

function error(message) {
    return new LogEntry(Level.ERROR, message);
}

/**
 * Custom implementation of Winston logger
 */
var _Logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({
            formatter: function (options) {
                var level = formatLevel(options.level);
                var timestamp = formatTimestamp();

                return timestamp
                    + ' '
                    + level
                    + ' '
                    + (options.message ? options.message : '')
                    + ' '
                    + (options.meta && Object.keys(options.meta).length ? '\n\t' + JSON.stringify(options.meta) : '' );
            }
        })
    ]
});

/**
 * Log entry model
 * @param level
 * @param message
 * @constructor
 */
function LogEntry(level, message) {
    this.level = level;
    this.message = message;
    this.keyValues = [];
    this._exception = null;

    this.keyValue = function (key, value) {
        this.keyValues.push({key: key, value: value});
        return this;
    };

    this.exception = function (value) {
        this._exception = value;
        return this;
    };

    this.json = function (value) {
        this._json = value;
        return this;
    };

    this.user = function(user) {
        if (user instanceof User) {
            this.keyValue('user', user.email);
        }
        return this;
    };

    this.build = function () {
        var entry = message;

        for (var i = 0; i < this.keyValues.length; i++) {
            entry += ' "' + this.keyValues[i].key + '"="' + this.keyValues[i].value + '"';
        }

        if (this._exception) {
            entry += ' "Exception":"' + this._exception + '"';
        }

        if (this._json) {
            entry += ' ' + JSON.stringify(this._json);
        }

        switch (this.level) {
            case Level.INFO:
                _Logger.info(entry);
                break;
            case Level.WARN:
                _Logger.warn(entry);
                break;
            case Level.ERROR:
                _Logger.error(entry);
                break;
        }
    };
}

function formatTimestamp() {
    return moment().format('MM/DD/YYYY HH:mm:ss.SSS');
}

function formatLevel(level) {
    level = level.toUpperCase();
    while (level.length < 5) {
        level += ' ';
    }
    return level;
}
