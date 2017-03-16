module.exports = function(sequelize, DataTypes) {
    return sequelize.define(
        'shopping_center',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'shopping_center_id',
                primaryKey: true
            },
            name: {
                type: DataTypes.STRING,
                validate: {
                    len: [3, 64],
                    notEmpty: true,
                }
            },
            nativeId: {
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
            url: {
                type: DataTypes.STRING,
                allowNull: true,
                validate: {
                    isUrl: true,
                    len: [3, 256]
                }
            }
        }
    );
};