function UserProfile(email, first_name, last_name) {
    this.email = email;
    this.first_name = first_name;
    this.last_name = last_name;
    this.user_identities = [];
}

UserProfile.prototype.displayName = function() {
    return this.first_name + ' ' + this.last_name;
};

UserProfile.build = function(data) {
    var user = new UserProfile(data.email, data.first_name, data.last_name);
    user.created_by = data.created_by;
    user.created_date = data.created_date;
    user.updated_by = data.updated_by;
    user.updated_date = data.updated_date;

    for (var i = 0; i < data.user_identities.length; i++) {
        user.user_identities.push(UserIdentity.build(data.user_identities[i]));
    }

    return user;
};