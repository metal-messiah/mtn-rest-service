module.exports = function(sequelize, DataTypes) {
    var ShoppingCenterAccess = sequelize.define(
        'shopping_center_access',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'shopping_center_access_id',
                primaryKey: true
            },
            shoppingCenterId: {
                type: DataTypes.INTEGER,
                field: 'shopping_center_id',
                allowNull: false
            },
            accessType: {
                type: DataTypes.STRING,
                field: 'access_type',
                values: ['FRONT_MAIN', 'SIDE_MAIN', 'NON_MAIN'],
                allowNull: false,
                validate: {
                    isIn: [['FRONT_MAIN', 'SIDE_MAIN', 'NON_MAIN']]
                }
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
                field: 'has_left_in',
                allowNull: false,
                defaultValue: false
            },
            hasLeftOut: {
                type: DataTypes.BOOLEAN,
                field: 'has_left_out',
                allowNull: false,
                defaultValue: false
            },
            hasRightIn: {
                type: DataTypes.BOOLEAN,
                field: 'has_right_in',
                allowNull: false,
                defaultValue: false
            },
            hasRightOut: {
                type: DataTypes.BOOLEAN,
                field: 'has_right_out',
                allowNull: false,
                defaultValue: false
            },
            hasTrafficLight: {
                type: DataTypes.BOOLEAN,
                field: 'has_traffic_light',
                allowNull: false,
                defaultValue: false
            },
            isOneWayRoad: {
                type: DataTypes.BOOLEAN,
                field: 'is_one_way_road',
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

    return ShoppingCenterAccess;
};