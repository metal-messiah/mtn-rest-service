var Logger = require('../util/logger.js');

module.exports = UserActiveMiddleware;

///////////////////////

function UserActiveMiddleware(req, res, next) {
    if (!req.mtnUser) {
        Logger.error('No mtnUser found on request!').build();
        res.status(500).send();
    } else if (!req.mtnUser.isActive()) {
        Logger.warn('User is not active')
            .keyValue('userId', req.mtnUser.id)
            .keyValue('email', req.mtnUser.email)
            .build();
        res.status(401).send('Unauthorized');
    } else {
        next();
    }
}