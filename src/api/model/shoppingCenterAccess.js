module.exports = function(sequelize, DataTypes) {
    return sequelize.define(
        'shopping_center_access',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'shopping_center_id',
                primaryKey: true
            },
            accessType: {
                type: DataTypes.ENUM,
                values: ['FRONT_MAIN', 'SIDE_MAIN', 'NON_MAIN'],
                allowNull: false
            },
            createdDate: {
                type: DataTypes.DATE,
                field: 'created_date'
            },
            deletedDate: {
                type: DataTypes.DATE,
                field: 'deleted_date'
            },
            hasLeftIn: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            hasLeftOut: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            hasRightIn: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            hasRightOut: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            hasTrafficLight: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            isOneWayRoad: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            updatedDate: {
                type: DataTypes.DATE,
                field: 'updated_date'
            },
            version: {
                type: DataTypes.INTEGER
            }
        }
    );
};