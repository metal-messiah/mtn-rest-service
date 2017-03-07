var _ = require('lodash');

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

    //Now check for environment variable
    var args = process.argv.slice(2);
    if (args.length) {
        var envArg = _.find(args, function (arg) {
            return arg.indexOf('app.env') === 0;
        });

        //If we found an environment variable, attempt to load the corresponding config file
        if (envArg) {
            var env = envArg.split('=')[1];

            if (env) {
                try {
                    var environmentProperties = require('../../resources/config-' + env + '.js');
                    _.assign(Properties, environmentProperties);
                } catch (e) {
                    //Don't add the properties
                }
            }
        }
    }
}