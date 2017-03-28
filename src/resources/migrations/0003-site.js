module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    return createTable()
        .then(createShoppingCenterIdIndex)
        .then(createSiteLocationIndex)
        .then(createSiteStateCountyIndex);

    /////////////////////

    function createTable() {
        return queryInterface.createTable(
            'site',
            {
                site_id: {
                    type: DataTypes.INTEGER,
                    primaryKey: true,
                    autoIncrement: true
                },
                shopping_center_id: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    references: {
                        model: 'shopping_center',
                        key: 'shopping_center_id'
                    }
                },
                address_1: {
                    type: DataTypes.STRING(64)
                },
                address_2: {
                    type: DataTypes.STRING(64)
                },
                city: {
                    type: DataTypes.STRING(64)
                },
                county: {
                    type: DataTypes.STRING(64)
                },
                country: {
                    type: DataTypes.STRING(64)
                },
                created_date: {
                    type: DataTypes.DATE,
                    allowNull: false
                },
                deleted_date: {
                    type: DataTypes.DATE
                },
                footprint_sqft: {
                    type: DataTypes.INTEGER
                },
                intersection_street_primary: {
                    type: DataTypes.STRING(64)
                },
                intersection_street_secondary: {
                    type: DataTypes.STRING(64)
                },
                intersection_quad: {
                    type: DataTypes.STRING(16)
                },
                location: {
                    type: DataTypes.GEOMETRY('POINT'),
                    allowNull: false
                },
                location_type: {
                    type: DataTypes.STRING(64)
                },
                position_in_center: {
                    type: DataTypes.STRING(64)
                },
                postal_code: {
                    type: DataTypes.STRING(64)
                },
                state: {
                    type: DataTypes.STRING(64)
                },
                type: {
                    type: DataTypes.STRING(64),
                    allowNull: false
                },
                updated_date: {
                    type: DataTypes.DATE,
                    allowNull: false
                },
                version: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    defaultValue: 1
                }
            }
        );
    }

    function createShoppingCenterIdIndex() {
        return queryInterface.addIndex(
            'site',
            ['shopping_center_id'],
            {
                indexName: 'idx_site_shopping_center_id',
                indexType: 'BTREE'
            }
        );
    }

    function createSiteLocationIndex() {
        return queryInterface.addIndex(
            'site',
            ['location'],
            {
                indexName: 'idx_site_location',
                indicesType: 'SPATIAL',
                indexType: 'GIST'
            }
        );
    }

    function createSiteStateCountyIndex() {
        return queryInterface.addIndex(
            'site',
            ['state', 'county'],
            {
                indexName: 'idx_site_state_county',
                indexType: 'BTREE'
            }
        );
    }
}

function down(queryInterface, DataTypes) {

    return dropTable();

    ///////////////////////////

    function dropTable() {
        return queryInterface.dropTable('site');
    }
}