module.exports = User;

/////////////////////////////////

function User(id, email) {
    this.id = id;
    this.email = email;
}

User.build = function(auth0Profile) {
    var userId = getUserId(auth0Profile);
    var email = getEmail(auth0Profile);

    return new User(userId, email);
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