var q = require('q');

/////////////////////////////////

module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up() {
    var deferred = q.defer();

    //Script logic here, call resolve() or reject() as appropriate
    deferred.resolve();

    return deferred.promise;
}

function down() {
    var deferred = q.defer();

    //Script logic here, call resolve() or reject() as appropriate
    deferred.resolve();

    return deferred.promise;
}