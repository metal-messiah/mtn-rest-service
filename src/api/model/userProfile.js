module.exports = function(sequelize, DataTypes) {
    var UserProfile = sequelize.define(
        'user_profile',
        {
            id: {
                type: DataTypes.INTEGER,
                primaryKey: true
            },
            created_date: {
                type: DataTypes.DATE
            },
            deleted_date: {
                type: DataTypes.DATE
            },
            email: {
                type: DataTypes.STRING,
                allowNull: false,
                unique: true,
                validate: {
                    isEmail: true
                }
            },
            first_name: {
                type: DataTypes.STRING
            },
            last_name: {
                type: DataTypes.STRING
            },
            updated_date: {
                type: DataTypes.DATE
            }
        },
        {
            classMethods: {
                associate: function(models) {
                    UserProfile.hasMany(models.UserIdentity);
                    UserProfile.hasOne(models.UserProfile, {as: 'CreatedBy', foreignKey: 'created_by'});
                    UserProfile.hasOne(models.UserProfile, {as: 'UpdatedBy', foreignKey: 'updated_by'});
                }
            },
            version: false,
            defaultScope: {
                where: {
                    email: {
                        $not: 'system.administrator@mtnra.com'
                    }
                }
            }
        }
    );

    return UserProfile;
};