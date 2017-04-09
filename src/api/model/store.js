module.exports = function(sequelize, DataTypes) {
    var Store = sequelize.define(
        'store',
        {
            id: {
                type: DataTypes.INTEGER,
                primaryKey: true
            },
            area_is_estimate: {
                type: DataTypes.BOOLEAN,
                allowNull: false
            },
            area_sales: {
                type: DataTypes.INTEGER
            },
            area_sales_percent_of_total: {
                type: DataTypes.DOUBLE
            },
            area_total: {
                type: DataTypes.INTEGER
            },
            closed_date: {
                type: DataTypes.DATE
            },
            created_date: {
                type: DataTypes.DATE
            },
            deleted_date: {
                type: DataTypes.DATE
            },
            fit: {
                type: DataTypes.STRING,
                values: ['CLUB', 'CONVENTIONAL', 'DISCOUNT', 'HISPANIC','NATURAL_FOODS', 'SUPERCENTER','WAREHOUSE', 'TRADER_JOES', 'QUALITY_SERVICE', 'WHOLE_FOODS', 'SPROUTS', 'ALDI', 'SAVE_A_LOT'],
                validate: {
                    isIn: [['CLUB', 'CONVENTIONAL', 'DISCOUNT', 'HISPANIC','NATURAL_FOODS', 'SUPERCENTER','WAREHOUSE', 'TRADER_JOES', 'QUALITY_SERVICE', 'WHOLE_FOODS', 'SPROUTS', 'ALDI', 'SAVE_A_LOT']]
                }
            },
            format: {
                type: DataTypes.STRING,
                values: ['ASIAN', 'CLUB', 'COMBO', 'CONVENTIONAL', 'DISCOUNT', 'HISPANIC', 'INDEPENDENT', 'INTERNATIONAL', 'LIMITED_ASSORTMENT', 'NATURAL_FOODS', 'SUPERCENTER', 'SUPERMARKET', 'WAREHOUSE', 'TRADER_JOES'],
                validate: {
                    isIn: [['ASIAN', 'CLUB', 'COMBO', 'CONVENTIONAL', 'DISCOUNT', 'HISPANIC', 'INDEPENDENT', 'INTERNATIONAL', 'LIMITED_ASSORTMENT', 'NATURAL_FOODS', 'SUPERCENTER', 'SUPERMARKET', 'WAREHOUSE', 'TRADER_JOES']]
                }
            },
            is_active: {
                type: DataTypes.BOOLEAN,
                allowNull: false
            },
            name: {
                type: DataTypes.STRING
            },
            opened_date: {
                type: DataTypes.DATE
            },
            updated_date: {
                type: DataTypes.DATE
            },
            version: {
                type: DataTypes.INTEGER
            }
        }
    );

    return Store;
};