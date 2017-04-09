module.exports = function(sequelize, DataTypes) {
    var Site = sequelize.define(
        'site',
        {
            id: {
                type: DataTypes.INTEGER,
                primaryKey: true
            },
            address_1: {
                type: DataTypes.STRING
            },
            address_2: {
                type: DataTypes.STRING
            },
            city: {
                type: DataTypes.STRING
            },
            county: {
                type: DataTypes.STRING
            },
            country: {
                type: DataTypes.STRING
            },
            created_date: {
                type: DataTypes.DATE
            },
            deleted_date: {
                type: DataTypes.DATE
            },
            footprint_sqft: {
                type: DataTypes.INTEGER
            },
            intersection_street_primary: {
                type: DataTypes.STRING
            },
            intersection_street_secondary: {
                type: DataTypes.STRING
            },
            intersection_quad: {
                type: DataTypes.STRING
            },
            location: {
                type: DataTypes.GEOMETRY('POINT'),
                allowNull: false
            },
            location_type: {
                type: DataTypes.STRING,
                values: ['HARD_CORNER', 'SOFT_CORNER', 'MID_BLOCK'],
                allowNull: false,
                validate: {
                    isIn: [['HARD_CORNER', 'SOFT_CORNER', 'MID_BLOCK']]
                }
            },
            position_in_center: {
                type: DataTypes.STRING
            },
            postal_code: {
                type: DataTypes.STRING
            },
            state: {
                type: DataTypes.STRING
            },
            type: {
                type: DataTypes.STRING,
                allowNull: false,
                values: ['PLACEHOLDER', 'ANCHOR', 'DEFAULT'],
                validate: {
                    isIn: [['PLACEHOLDER', 'ANCHOR', 'DEFAULT']]
                }
            },
            updated_date: {
                type: DataTypes.DATE
            },
            version: {
                type: DataTypes.INTEGER
            }
        }
    );

    return Site;
};