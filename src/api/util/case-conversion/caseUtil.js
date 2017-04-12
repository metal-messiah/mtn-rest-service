var changeCase = require('change-case');
var _ = require('lodash');

module.exports = {
    convert: convert
};

//////////////////////////

function convert(value, conversionCallback) {
    var newValue, newKey;

    if (value instanceof Array) {
        newValue = [];
        for (var i = 0; i < value.length; i++) {
            if (typeof value[i] === 'object') {
                newValue.push(convert(value[i], conversionCallback));
            } else {
                newValue.push(value[i]);
            }
        }
    } else {
        newValue = {};
        for (var property in value) {
            if (value.hasOwnProperty(property)) {
                newKey = conversionCallback(property);

                //If going to camelCase and property contains a number, changeCase won't automatically strip the '_'
                if (conversionCallback === changeCase.camelCase && isAlphaNumeric(newKey)) {
                    newKey = stripUnderscore(newKey);
                }

                newValue[newKey] = value[property];
            }
        }
    }

    return newValue;
}

function isAlphaNumeric(value) {
    return !!value.match(/\d+/g);
}

function stripUnderscore(value) {
    return value.replace(/[_]/g, '');
}