var changeCase = require('change-case');

var Errors = require('../error/errors.js');
var CaseUtil = require('./case-conversion/caseUtil.js');

/**
 * This service provides a clean and consistent way to handle HTTP
 * responses, particularly with error responses.
 */
module.exports = {
    error: error,
    ok: ok
};

////////////////////////////

/**
 * This expects an instance of an Error defined in Errors.
 *
 * If the given Error is not of this type, it will return
 * a 500 Internal Server Error.
 *
 * If the given Error is of this type, the default 500 status
 * and error message will be overridden with the status and
 * message found in the Error.
 */
function error(res, error) {
    var data = {
        message: 'An Unexpected Error Occurred'
    };

    var status = 500;

    /*
    Only pass error message through to response if it came
     */
    if (error && error.status) {
        status = error.status;

        //Replace error response body with conflicted data if ConflictError
        if (error instanceof Errors.ConflictError) {
            data = error.extra;
        } else {
            data.message = error.message;
        }
    }

    res.status(status).json(data);
}

function ok(res, data) {
    if (data) {
        /*
        Until I can find a cleaner way to do this, this is it. The response body may
        or may not be a Sequelize model instance (which is NOT a raw JSON object, and
        is NOT easily converted to raw JSON without this type of approach).

        So, stringifying, then re-parsing the object guarantees that we'll be passing
        the true response body object into the conversion.
         */
        data = JSON.stringify(data);
        data = CaseUtil.convert(JSON.parse(data), changeCase.camelCase);
        res.status(200).json(data);
    } else {
        res.status(204).send();
    }
}