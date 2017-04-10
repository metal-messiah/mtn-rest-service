function UserIdentity(provider, provider_user_id) {
    this.provider = provider;
    this.provider_user_id = provider_user_id;
}

UserIdentity.build = function(data) {
    return new UserIdentity(data.provider, data.provider_user_id);
};