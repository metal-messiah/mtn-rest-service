module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    var query = "" +
        "INSERT INTO user_profile (created_by, created_date, email, first_name, last_name, updated_by, updated_date) VALUES " +
        "(1, NOW(), 'system.administrator@mtnra.com', 'System', 'Administrator', 1, NOW())";
    return queryInterface.sequelize.query(query);
}

function down(queryInterface, DataTypes) {
    var query = "" +
        "DELETE FROM user_profile WHERE email = 'system.administrator@mtnra.com'";
    return queryInterface.sequelize.query(query);
}