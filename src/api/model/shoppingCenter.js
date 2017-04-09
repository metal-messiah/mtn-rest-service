module.exports = function(sequelize, DataTypes) {
    var ShoppingCenter = sequelize.define(
        'shopping_center',
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
            name: {
                type: DataTypes.STRING,
                allowNull: false,
                validate: {
                    len: [3, 64],
                    notEmpty: true
                }
            },
            native_id: {
                type: DataTypes.STRING,
                allowNull: true,
                validate: {
                    len: [1, 64]
                }
            },
            owner: {
                type: DataTypes.STRING,
                allowNull: true,
                validate: {
                    len: [3, 64]
                }
            },
            updated_date: {
                type: DataTypes.DATE
            },
            url: {
                type: DataTypes.STRING,
                allowNull: true,
                validate: {
                    isUrl: true,
                    len: [3, 256]
                }
            },
            version: {
                type: DataTypes.INTEGER
            }
        }
    );

    return ShoppingCenter;
};