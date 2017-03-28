module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    return createTable()
        .then(createSiteIdIndex)
        .then(createNameIndex);

    ///////////////////////////////

    function createTable() {
        return queryInterface.createTable(
            'store',
            {
                store_id: {
                    type: DataTypes.INTEGER,
                    primaryKey: true,
                    autoIncrement: true
                },
                site_id: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    references: {
                        model: 'site',
                        key: 'site_id'
                    }
                },
                area_is_estimate: {
                    type: DataTypes.BOOLEAN,
                    allowNull: false,
                    defaultValue: true
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
                    type: DataTypes.DATE,
                    allowNull: false
                },
                deleted_date: {
                    type: DataTypes.DATE
                },
                fit: {
                    type: DataTypes.STRING(64)
                },
                format: {
                    type: DataTypes.STRING(64)
                },
                is_active: {
                    type: DataTypes.BOOLEAN,
                    allowNull: false,
                    defaultValue: false
                },
                name: {
                    type: DataTypes.STRING(64)
                },
                opened_date: {
                    type: DataTypes.DATE
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

    function createSiteIdIndex() {
        return queryInterface.addIndex(
            'store',
            ['site_id'],
            {
                indexName: 'idx_store_site_id',
                indexType: 'BTREE'
            }
        );
    }

    function createNameIndex() {
        return queryInterface.addIndex(
            'store',
            ['name'],
            {
                indexName: 'idx_store_name',
                indexType: 'BTREE'
            }
        );
    }

}

function down(queryInterface, DataTypes) {

    return dropTable();

    ///////////////////////////

    function dropTable() {
        return queryInterface.dropTable('store');
    }
}