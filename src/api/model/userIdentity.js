module.exports = function(sequelize, DataTypes) {
    return sequelize.define(
        'user_identity',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'user_identity_id',
                primaryKey: true
            },
            userProfileId: {
                type: DataTypes.INTEGER,
                field: 'user_profile_id',
                allowNull: false
            },
            provider: {
                type: DataTypes.STRING,
                allowNull: false
            },
            providerUserId: {
                type: DataTypes.STRING,
                allowNull: false
            }
        },
        {
            timestamps: false,
            version: false
        }
    );
};