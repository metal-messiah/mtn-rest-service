module.exports = AuthToken;

/////////////////////////////////

function AuthToken(aud, exp, iat, iss, sub) {
    this.aud = aud;
    this.exp = exp;
    this.iat = iat;
    this.iss = iss;
    this.sub = sub;
}

AuthToken.prototype.getProvider = function() {
    return this.sub ? this.sub.split('|')[0] : null;
};

AuthToken.prototype.getProviderUserId = function() {
    return this.sub ? this.sub.split('|')[1] : null;
};

AuthToken.build = function(data) {
    return new AuthToken(data.aud, data.exp, data.iat, data.iss, data.sub);
};