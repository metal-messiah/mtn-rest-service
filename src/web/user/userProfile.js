function UserProfile(email, firstName, lastName) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userIdentities = [];
}

UserProfile.prototype.displayName = function() {
    return this.firstName + ' ' + this.lastName;
};

UserProfile.build = function(data) {
    var user = new UserProfile(data.email, data.firstName, data.lastName);
    user.createdBy = data.createdBy;
    user.createdDate = data.createdDate;
    user.updatedBy = data.updatedBy;
    user.updatedDate = data.updatedDate;

    for (var i = 0; i < data.userIdentities.length; i++) {
        user.userIdentities.push(UserIdentity.build(data.userIdentities[i]));
    }

    return user;
};