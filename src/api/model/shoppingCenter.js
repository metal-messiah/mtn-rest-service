module.exports = function(sequelize, DataTypes) {
    return sequelize.define(
        'shopping_center',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'shopping_center_id',
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
            name: {
                type: DataTypes.STRING,
                allowNull: false,
                validate: {
                    len: [3, 64],
                    notEmpty: true
                }
            },
            nativeId: {
                type: DataTypes.STRING,
                field: 'native_id',
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
            updatedDate: {
                type: DataTypes.DATE,
                field: 'updated_date'
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