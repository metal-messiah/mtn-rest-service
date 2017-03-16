module.exports = {
    UnauthorizedError: extendError('UnauthorizedError')
};

//////////////////////////////////

function extendError(name) {
    function E(message, extra) {
        Error.captureStackTrace(this, this.constructor);
        this.name = name;
        this.message = message;
        this.extra = extra;
    }
    E.prototype = new Error();
    return E;
}