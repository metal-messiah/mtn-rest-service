module.exports = function(sequelize, DataTypes) {
    var Site = sequelize.define(
        'site',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'site_id',
                primaryKey: true
            },
            shoppingCenterId: {
                type: DataTypes.INTEGER,
                field: 'shopping_center_id',
                allowNull: false
            },
            address1: {
                type: DataTypes.STRING,
                field: 'address_1'
            },
            address2: {
                type: DataTypes.STRING,
                field: 'address_2'
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
            createdDate: {
                type: DataTypes.DATE,
                field: 'created_date'
            },
            deletedDate: {
                type: DataTypes.DATE,
                field: 'deleted_date'
            },
            footprintSqft: {
                type: DataTypes.INTEGER,
                field: 'footprint_sqft'
            },
            intersectionStreetPrimary: {
                type: DataTypes.STRING,
                field: 'intersection_street_primary'
            },
            intersectionStreetSecondary: {
                type: DataTypes.STRING,
                field: 'intersection_street_secondary'
            },
            intersectionQuad: {
                type: DataTypes.STRING,
                field: 'intersection_quad'
            },
            location: {
                type: DataTypes.GEOMETRY('POINT'),
                allowNull: false
            },
            locationType: {
                type: DataTypes.STRING,
                field: 'location_type',
                values: ['HARD_CORNER', 'SOFT_CORNER', 'MID_BLOCK'],
                allowNull: false,
                validate: {
                    isIn: [['HARD_CORNER', 'SOFT_CORNER', 'MID_BLOCK']]
                }
            },
            positionInCenter: {
                type: DataTypes.STRING,
                field: 'position_in_center'
            },
            postalCode: {
                type: DataTypes.STRING,
                field: 'postal_code'
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
            updatedDate: {
                type: DataTypes.DATE,
                field: 'updated_date'
            },
            version: {
                type: DataTypes.INTEGER
            }
        }
    );

    return Site;
};