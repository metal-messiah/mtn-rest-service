var AuthToken = require('./authToken.js');

module.exports = AuthTokenMiddleware;

function AuthTokenMiddleware(req, res, next) {
    req.user = AuthToken.build(req.user);
    next();
}