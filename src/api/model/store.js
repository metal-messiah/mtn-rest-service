module.exports = function(sequelize, DataTypes) {
    var Store = sequelize.define(
        'store',
        {
            id: {
                type: DataTypes.INTEGER,
                field: 'store_id',
                primaryKey: true
            },
            siteId: {
                type: DataTypes.INTEGER,
                field: 'site_id',
                allowNull: false
            },
            areaIsEstimate: {
                type: DataTypes.BOOLEAN,
                field: 'area_is_estimate',
                allowNull: false
            },
            areaSales: {
                type: DataTypes.INTEGER,
                field: 'area_sales'
            },
            areaSalesPercentOfTotal: {
                type: DataTypes.DOUBLE,
                field: 'area_sales_percent_of_total'
            },
            areaTotal: {
                type: DataTypes.INTEGER,
                field: 'area_total'
            },
            closedDate: {
                type: DataTypes.DATE,
                field: 'closed_date'
            },
            createdDate: {
                type: DataTypes.DATE,
                field: 'created_date'
            },
            deletedDate: {
                type: DataTypes.DATE,
                field: 'deleted_date'
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
            isActive: {
                type: DataTypes.BOOLEAN,
                field: 'is_active',
                allowNull: false
            },
            name: {
                type: DataTypes.STRING
            },
            openedDate: {
                type: DataTypes.DATE,
                field: 'opened_date'
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

    return Store;
};