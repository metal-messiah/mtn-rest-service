var _pattern = /^([\d]{1,2}[d]{1}[\s]?)?([\d]{1,2}[h]{1}[\s]?)?([\d]{1,2}[m]{1}[\s]?)?([\d]{1,2}[s]{1})?$/;
var _secondsMultiplier = 1000;
var _minutesMultiplier = _secondsMultiplier * 60;
var _hoursMultiplier = _minutesMultiplier * 60;
var _daysMultiplier = _hoursMultiplier * 24;

/**
 * A simple service for converting long time values to/from string duration values
 *
 * Ex "1h 2m 3s"
 */
var Duration = {
    validate: validate,
    stringToLong: stringToLong,
    longToString: longToString
};

module.exports = Duration;

/////////////////////////////////

function validate(value) {
    try {
        validateString(value);
        return true;
    } catch(e) {
        return false;
    }
}

function stringToLong(value) {
    validateString(value);
    return convertString(value);
}

function longToString(value) {
    validateNumber(value);
    return convertNumber(value);
}

function validateString(value) {
    if (!value || !value.toLowerCase().match(_pattern)) {
        throw new Error('Invalid duration value: ' + value);
    }
}

function validateNumber(value) {
    if (!value || typeof value !== 'number' || (value%1) !== 0 || value <= 0) {
        throw new Error('Invalid duration value: ' + value);
    }
}

function convertString(value) {
    var result = 0;

    var parts = value.trim().split(' ');
    for (var i = 0; i < parts.length; i++) {
        var part = parts[i].trim();

        var postfix = part.slice(-1);
        var prefix = Number(part.replace(/[\D]/, ''));
        switch (postfix) {
            case 'd':
                result += prefix * _daysMultiplier;
                break;
            case 'h':
                result += prefix * _hoursMultiplier;
                break;
            case 'm':
                result += prefix * _minutesMultiplier;
                break;
            case 's':
                result += prefix * _secondsMultiplier;
                break;
        }
    }

    return result;
}

function convertNumber(value) {
    var result = '';

    if (value >= _daysMultiplier) {
        result += Math.floor(value / _daysMultiplier) + 'd ';
        value = value % _daysMultiplier;
    }
    if (value >= _hoursMultiplier) {
        result += Math.floor(value / _hoursMultiplier) + 'h ';
        value = value % _hoursMultiplier;
    }
    if (value >= _minutesMultiplier) {
        result += Math.floor(value / _minutesMultiplier) + 'm ';
        value = value % _minutesMultiplier;
    }
    if (value > 0) {
        result += Math.ceil(value / _secondsMultiplier) + 's';
    }

    return result;
}
