function User(id, email) {
    this.id = id;
    this.email = email;
}

User.build = function(profile) {
    var userId = getUserId(profile);
    var email = getEmail(profile);

    return new User(userId, email);
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