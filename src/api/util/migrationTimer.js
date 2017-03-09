var moment = require('moment');

var Duration = require('./duration.js');

////////////////////////////////

var service = {
    scripts: {},
    start: start,
    stop: stop
};

module.exports = service;

////////////////////////////////

/**
 * Records start time of given script
 */
function start(script) {
    service.scripts[script] = moment();
}

/**
 * Calculates and returns duration of given script as a string
 */
function stop(script) {
    var start = service.scripts[script];
    if (!start) {
        return 'Duration Not Available';
    }

    var diff = moment().diff(start);
    delete service.scripts[script];
    return Duration.longToString(diff);
}