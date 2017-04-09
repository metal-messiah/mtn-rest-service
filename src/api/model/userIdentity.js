module.exports = function(sequelize, DataTypes) {
    var UserIdentity = sequelize.define(
        'user_identity',
        {
            id: {
                type: DataTypes.INTEGER,
                primaryKey: true
            },
            provider: {
                type: DataTypes.STRING,
                allowNull: false
            },
            provider_user_id: {
                type: DataTypes.STRING,
                allowNull: false
            }
        },
        {
            classMethods: {
                associate: function(models) {
                    UserIdentity.belongsTo(models.UserProfile);
                }
            },
            timestamps: false,
            version: false
        }
    );

    return UserIdentity;
};