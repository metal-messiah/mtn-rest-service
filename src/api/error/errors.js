/**
 * The goal of this service is to provide some standard error types we can throw and
 * validate against throughout the app, allowing instanceof checks. Since in the end,
 * any error is going to be converted to an HTTP status code, I opted to have these
 * errors conform to the HTTP error codes, more or less.
 */
module.exports = {
    BadRequestError: extendError('BadRequestError', 400, 'Bad Request'),
    ConflictError: extendError('ConflictError', 417, 'The version of your data is out-of-date'),
    InternalServerError: extendError('InternalServerError', 500, 'An Unexpected Error Occurred'),
    UnauthorizedError: extendError('UnauthorizedError', 401, 'You do not have permission to perform this action')
};

//////////////////////////////////

/**
 * This extends the basic Javascript Error type, but adds
 * some custom functionality.
 *
 * The constructor can be used just as with the basic Error,
 * but if the first argument is NOT a string, it will instead
 * treat the first argument, if any, as "extra".
 *
 * This allows us to set default messages for specific error
 * types.
 */
function extendError(name, status, defaultMessage) {
    function E(message, extra) {
        Error.captureStackTrace(this, this.constructor);
        this.name = name;

        //If message is a string, populate Error normally
        if (typeof message === 'string') {
            this.message = message;
            this.extra = extra;
        }
        //Else, set the first arg to extra, instead
        else {
            this.message = this.defaultMessage || 'An Unexpected Error Occurred';
            this.extra = message;
        }

        if (!this.status) {
            this.status = 500;
        }
    }
    E.prototype = new Error();
    E.prototype.status = status;

    if (defaultMessage) {
        E.prototype.defaultMessage = defaultMessage;
        E.prototype.message = defaultMessage;
    }

    return E;
}