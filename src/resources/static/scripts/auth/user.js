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

User.build = function(profile) {
    var userId = getUserId(profile);
    var email = getEmail(profile);
    var permissions = getPermissions(profile);

    return new User(userId, email, permissions);
};

function getUserId(profile) {
    var identity = profile.identities ? profile.identities[0] : null;
    if (!identity || !identity['user_id']) {
        throw new Error('No user_id found for User!');
    }
    return identity['user_id'];
}

function getEmail(profile) {
    if (!profile.email) {
        throw new Error('No email found for User!');
    }
    return profile.email;
}

function getPermissions(profile) {
    var appMetadata = profile['app_metadata'];
    if (!appMetadata || !appMetadata.permissions) {
        throw new Error('No permissions found for User!');
    }
    return appMetadata.permissions;
}