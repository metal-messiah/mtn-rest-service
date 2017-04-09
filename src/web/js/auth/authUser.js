function AuthUser(id, provider, email) {
    this.id = id;
    this.provider = provider;
    this.email = email;
}

AuthUser.build = function(profile) {
    var userId = profile.user_id;
    var userIdParts = userId.split('|');

    return new AuthUser(userIdParts[1], userIdParts[0], profile.email);
};