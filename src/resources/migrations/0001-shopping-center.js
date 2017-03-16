module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {

    return createTable()
        .then(createNameIndex)
        .then(createOwnerIndex)
        .then(createNativeIdIndex);

    //////////////////////////////////

    function createTable() {
        return queryInterface.createTable(
            'shopping_center',
            {
                shopping_center_id: {
                    type: DataTypes.INTEGER,
                    primaryKey: true,
                    autoIncrement: true
                },
                created_date: {
                    type: DataTypes.DATE,
                    allowNull: false
                },
                name: {
                    type: DataTypes.STRING,
                    allowNull: false
                },
                native_id: {
                    type: DataTypes.STRING
                },
                owner: {
                    type: DataTypes.STRING
                },
                updated_date: {
                    type: DataTypes.DATE,
                    allowNull: false
                },
                url: {
                    type: DataTypes.STRING
                },
                version: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    defaultValue: 1
                }
            }
        );
    }

    function createNameIndex() {
        return queryInterface.addIndex(
            'shopping_center',
            ['name'],
            {
                indexName: 'idx_shopping_center_name',
                indexType: 'BTREE'
            }
        );
    }

    function createOwnerIndex() {
        return queryInterface.addIndex(
            'shopping_center',
            ['owner'],
            {
                indexName: 'idx_shopping_center_owner',
                indexType: 'BTREE'
            }
        );
    }

    function createNativeIdIndex() {
        return queryInterface.addIndex(
            'shopping_center',
            ['native_id'],
            {
                indexName: 'idx_shopping_center_native_id',
                indexType: 'BTREE'
            }
        );
    }
}

function down(queryInterface, DataTypes) {

    return dropTable();

    ///////////////////////////

    function dropTable() {
        return queryInterface.dropTable('shopping_center');
    }
}
