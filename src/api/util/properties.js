var _ = require('lodash');

var Logger = require('./logger.js');

/**
 * An injectable object that will have all application properties read in.
 *
 * Uses the app.env argument to load environment properties.
 */
var Properties = {};

loadProperties();

module.exports = Properties;

/////////////////////////////////

function loadProperties() {
    //Load global properties first
    var applicationProperties = require('../../resources/config.js');
    _.assign(Properties, applicationProperties);

    //Check process variable first (Heroku)
    var env = process.env['app.env'];

    //If no process variable, check process args (Local)
    if (!env) {
        //Now check for environment variable
        var args = process.argv.slice(2);
        if (args.length) {
            var envArg = _.find(args, function (arg) {
                return arg.indexOf('app.env') === 0;
            });

            //If we found an environment variable, attempt to load the corresponding config file
            if (envArg) {
                env = envArg.split('=')[1];
            }
        }
    }

    Logger.info('Environment variable: ' + envArg).build();

    if (env) {
        try {
            var environmentProperties = require('../../resources/config-' + env + '.js');
            _.assign(Properties, environmentProperties);
        } catch (e) {
            //Don't add the properties
        }
    }

}