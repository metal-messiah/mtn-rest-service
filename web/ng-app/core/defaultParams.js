/**
 * This sets the default pagination params, and allows the addition
 * of custom params.
 *
 * For now, accept large page sizes, until such time as pagination is
 * truly needed.
 */
function DefaultParams() {
    this.params = {
        size: 1000,
        page: 0
    };
}

DefaultParams.prototype.add = function (key, value) {
    this.params[key] = value;
};