var q = require('q');

module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    return executeQueries(upQueries(), queryInterface);
}

function down(queryInterface, DataTypes) {
    return executeQueries(downQueries(), queryInterface);
}

/////////////////////////////////

function executeQueries(queries, queryInterface) {
    return queryInterface.sequelize.transaction(function(t) {
        var promises = [];

        for (var i = 0; i < queries.length; i++) {
            var query = queries[i];
            promises.push(q(queryInterface.sequelize.query(query, {transaction: t})));
        }

        return q.all(promises);
    });
}

function upQueries() {
    return [
        "ALTER TABLE user_profile RENAME COLUMN user_profile_id TO id",
        "ALTER TABLE user_identity RENAME COLUMN user_identity_id TO id",
        "ALTER TABLE shopping_center RENAME COLUMN shopping_center_id TO id",
        "ALTER TABLE shopping_center_access RENAME COLUMN shopping_center_access_id TO id",
        "ALTER TABLE site RENAME COLUMN site_id TO id",
        "ALTER TABLE store RENAME COLUMN store_id TO id"
    ];
}

function downQueries() {
    return [
        "ALTER TABLE user_profile RENAME COLUMN id TO user_profile_id",
        "ALTER TABLE user_identity RENAME COLUMN id TO user_identity_id",
        "ALTER TABLE shopping_center RENAME COLUMN id TO shopping_center_id",
        "ALTER TABLE shopping_center_access RENAME COLUMN id TO shopping_center_access_id",
        "ALTER TABLE site RENAME COLUMN id TO site_id",
        "ALTER TABLE store RENAME COLUMN id TO store_id"
    ];
}