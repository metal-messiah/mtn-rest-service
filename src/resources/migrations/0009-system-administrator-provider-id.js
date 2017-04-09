module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    var query = "" +
        "INSERT INTO user_identity (user_profile_id, provider, provider_user_id) VALUES " +
        "((SELECT user_profile_id FROM user_profile WHERE email = 'system.administrator@mtnra.com'), 'auth0', '58e2c990319f9f0ebc00e057')";
    return queryInterface.sequelize.query(query);
}

function down(queryInterface, DataTypes) {
    var query = "" +
        "DELETE FROM user_identity WHERE provider_user_id = '58e2c990319f9f0ebc00e057'";
    return queryInterface.sequelize.query(query);
}