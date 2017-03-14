module.exports = User;

/////////////////////////////////

function User(id, email, permissions) {
    this.id = id;
    this.email = email;
    this.permissions = {
        isActive: permissions ? !!permissions.isActive : false,
        isAdmin: permissions ? !!permissions.isAdmin : false
    };
}

User.prototype.isActive = function() {
    return this.permissions.isActive;
};

User.prototype.isAdmin = function() {
    return this.permissions.isAdmin;
};

User.build = function(auth0Profile) {
    var userId = getUserId(auth0Profile);
    var email = getEmail(auth0Profile);
    var permissions = getPermissions(auth0Profile);

    return new User(userId, email, permissions);
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
    var appMetadata = auth0Profile['app_metadata'];
    if (!appMetadata || !appMetadata.permissions) {
        throw new Error('No permissions found for User!');
    }
    return appMetadata.permissions;
}

