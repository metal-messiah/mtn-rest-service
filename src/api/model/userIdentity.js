module.exports = function(sequelize, DataTypes) {
    var UserIdentity = sequelize.define(
        'user_identity',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'user_identity_id',
                primaryKey: true
            },
            // userProfileId: {
            //     type: DataTypes.INTEGER,
            //     field: 'user_profile_id',
            //     allowNull: false
            // },
            provider: {
                type: DataTypes.STRING,
                allowNull: false
            },
            providerUserId: {
                type: DataTypes.STRING,
                field: 'provider_user_id',
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