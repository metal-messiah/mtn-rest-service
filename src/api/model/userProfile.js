module.exports = function(sequelize, DataTypes) {
    var UserProfile = sequelize.define(
        'user_profile',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'user_profile_id',
                primaryKey: true
            },
            createdDate: {
                type: DataTypes.DATE,
                field: 'created_date'
            },
            deletedDate: {
                type: DataTypes.DATE,
                field: 'deleted_date'
            },
            email: {
                type: DataTypes.STRING,
                allowNull: false,
                unique: true,
                validate: {
                    isEmail: true
                }
            },
            firstName: {
                type: DataTypes.STRING,
                field: 'first_name'
            },
            lastName: {
                type: DataTypes.STRING,
                field: 'last_name'
            },
            updatedDate: {
                type: DataTypes.DATE,
                field: 'updated_date'
            }
        },
        {
            classMethods: {
                associate: function(models) {
                    UserProfile.hasMany(models.UserIdentity)
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