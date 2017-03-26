module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {

    return createTable()
        .then(createShoppingCenterIdIndex);

    /////////////////////////////

    function createTable() {
        return queryInterface.createTable(
            'shopping_center_access',
            {
                shopping_center_access_id: {
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
                access_type: {
                    type: DataTypes.STRING(64),
                    allowNull: false
                },
                created_date: {
                    type: DataTypes.DATE,
                    allowNull: false
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
            'shopping_center_access',
            ['shopping_center_id'],
            {
                indexName: 'idx_shopping_center_access_shopping_center_id',
                indexType: 'BTREE'
            }
        );
    }
}

function down(queryInterface, DataTypes) {

    return dropTable();

    ///////////////////////////

    function dropTable() {
        return queryInterface.dropTable('shopping_center_access');
    }
}