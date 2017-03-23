var _ = require('lodash');

module.exports = {
    cleanUndefined: cleanUndefined,
    isNumeric: isNumeric,
    isPositiveInteger: isPositiveInteger
};

//////////////////////////////////

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