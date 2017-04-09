module.exports = function(sequelize, DataTypes) {
    var ShoppingCenterAccess = sequelize.define(
        'shopping_center_access',
        {
            id: {
                type: DataTypes.INTEGER,
                primaryKey: true
            },
            access_type: {
                type: DataTypes.STRING,
                values: ['FRONT_MAIN', 'SIDE_MAIN', 'NON_MAIN'],
                allowNull: false,
                validate: {
                    isIn: [['FRONT_MAIN', 'SIDE_MAIN', 'NON_MAIN']]
                }
            },
            created_date: {
                type: DataTypes.DATE
            },
            deleted_date: {
                type: DataTypes.DATE
            },
            has_left_in: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            has_left_out: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            has_right_in: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            has_right_out: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            has_traffic_light: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            is_one_way_road: {
                type: DataTypes.BOOLEAN,
                allowNull: false,
                defaultValue: false
            },
            updated_date: {
                type: DataTypes.DATE
            },
            version: {
                type: DataTypes.INTEGER
            }
        }
    );

    return ShoppingCenterAccess;
};