var Errors = require('../error/errors.js');

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
        data.message = error.message;
    }

    res.status(status).json(data);
}

function ok(res, data) {
    res.status(200).json(data);
}