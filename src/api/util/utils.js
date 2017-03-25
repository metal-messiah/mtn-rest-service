var _ = require('lodash');
var sequelize = require('./sequelizeInstance.js');

var Errors = require('../error/errors.js');

module.exports = {
    cleanUndefined: cleanUndefined,
    handleSequelizeException: handleSequelizeException,
    isNumeric: isNumeric,
    isPositiveInteger: isPositiveInteger
};

//////////////////////////////////

/**
 * Deep-cleans the given object or array by removing all undefined fields.
 */
function cleanUndefined(object) {
    for (var property in object) {
        if (object.hasOwnProperty(property)) {
            if (_.isUndefined(object[property])) {
                delete object[property];
            } else if (_.isArray(object[property])) {
                for (var i = 0; i < object[property].length; i++) {
                    cleanUndefined(object[property][i]);
                }
            } else if (_.isObject(object[property])) {
                cleanUndefined(object[property]);
            }
        }
    }
}

/**
 * Handles the Sequelize Error by converting it to one of our custom
 * Error types.
 */
function handleSequelizeException(error) {
    if (error instanceof sequelize.ValidationError) {
        var validationErrors = error.errors;
        var validationErrorMessages = [];

        //Collect just the validation messages from any child errors contained in the ValidationError
        for (var i = 0; i < validationErrors.length; i++) {
            validationErrorMessages.push(validationErrors[i].message);
        }

        throw new Errors.BadRequestError(validationErrorMessages.join(', '));
    } else {
        throw new Errors.InternalServerError();
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function isPositiveInteger(value) {
    if (isNumeric(value)) {
        var n = Math.floor(Number(value));
        return n > 0;
    }
    return false;
}