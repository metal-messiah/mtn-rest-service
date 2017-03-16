var Errors = require('../error/errors.js');

module.exports = User;

/////////////////////////////////

function User(id, email, permissions) {
    this.id = id;
    this.email = email;
    this.permissions = permissions;
}

/**
 * Throws an error if user doesn't have the given permission
 */
User.prototype.authorize = function(permission) {
    if (!this.permissions[permission]) {
        throw new Errors.UnauthorizedError('User does not have permission to perform this action', permission);
    }
};

/**
 * Simply returns boolean whether user has permission or not
 */
User.prototype.hasPermission = function(permission) {
    return this.permissions[permission];
};

User.build = function(auth0Profile) {
    var userId = getUserId(auth0Profile);
    var email = getEmail(auth0Profile);
    var permissions = getPermissions(auth0Profile);

    return new User(userId, email, permissions);
};

User.Permission = {
    CREATE_SHOPPING_CENTER: 'CREATE:SHOPPING_CENTER',
    READ_SHOPPING_CENTER: 'READ:SHOPPING_CENTER'
};

//////////////////////////////////

function getUserId(auth0Profile) {
    var identity = auth0Profile.identities ? auth0Profile.identities[0] : null;
    if (!identity || !identity['user_id']) {
        throw new Error('No user_id found for User!');
    }
    return identity['user_id'];
}

function getEmail(auth0Profile) {
    if (!auth0Profile.email) {
        throw new Error('No email found for User!');
    }
    return auth0Profile.email;
}

function getPermissions(auth0Profile) {
    if (!auth0Profile.authorization || !auth0Profile.authorization.permissions || !auth0Profile.authorization.permissions.length) {
        throw new Error('No permissions found for User!');
    }

    var permissions = {};

    for (var i = 0; i < auth0Profile.authorization.permissions.length; i++) {
        var permission = auth0Profile.authorization.permissions[i];
        permissions[permission] = true;
    }

    return permissions;
}