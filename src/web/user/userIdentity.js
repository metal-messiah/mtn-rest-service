function UserIdentity(provider, providerUserId) {
    this.provider = provider;
    this.providerUserId = providerUserId;
}

UserIdentity.build = function(data) {
    return new UserIdentity(data.provider, data.providerUserId);
};