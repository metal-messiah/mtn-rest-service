var q = require('q');

/////////////////////////////////

module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    var deferred = q.defer();

    //Script logic here, call resolve() or reject() as appropriate
    deferred.resolve();

    return deferred.promise;
}

function down(queryInterface, DataTypes) {
    var deferred = q.defer();

    //Script logic here, call resolve() or reject() as appropriate
    deferred.resolve();

    return deferred.promise;
}