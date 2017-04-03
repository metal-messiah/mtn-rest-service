module.exports = {
    up: up,
    down: down
};

/////////////////////////////////

function up(queryInterface, DataTypes) {
    return createUserTable()
        .then(createUserIdentityTable)
        .then(createUserIdIndex)
        .then(createEmailUniqueIndex)
        .then(createProviderUserIdIndex);

    ////////////////////////////////

    function createUserTable() {
        return queryInterface.createTable(
            'user_profile',
            {
                user_profile_id: {
                    type: DataTypes.INTEGER,
                    primaryKey: true,
                    autoIncrement: true
                },
                created_by: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    references: {
                        model: 'user_profile',
                        key: 'user_profile_id'
                    }
                },
                created_date: {
                    type: DataTypes.DATE,
                    allowNull: false
                },
                deleted_by: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    references: {
                        model: 'user_profile',
                        key: 'user_profile_id'
                    }
                },
                deleted_date: {
                    type: DataTypes.DATE
                },
                email: {
                    type: DataTypes.STRING(64),
                    allowNull: false
                },
                first_name: {
                    type: DataTypes.STRING(64)
                },
                last_name: {
                    type: DataTypes.STRING(64)
                },
                updated_by: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    references: {
                        model: 'user_profile',
                        key: 'user_profile_id'
                    }
                },
                updated_date: {
                    type: DataTypes.DATE,
                    allowNull: false
                }
            }
        );
    }

    function createUserIdentityTable() {
        return queryInterface.createTable(
            'user_identity',
            {
                user_identity_id: {
                    type: DataTypes.INTEGER,
                    primaryKey: true,
                    autoIncrement: true
                },
                user_profile_id: {
                    type: DataTypes.INTEGER,
                    allowNull: false,
                    references: {
                        model: 'user_profile',
                        key: 'user_profile_id'
                    }
                },
                provider: {
                    type: DataTypes.STRING(64),
                    allowNull: false
                },
                provider_user_id: {
                    type: DataTypes.STRING(64),
                    allowNull: false
                }
            }
        );
    }

    function createEmailUniqueIndex() {
        return queryInterface.addIndex(
            'user_profile',
            ['email'],
            {
                indexName: 'uk_user_profile_email',
                indicesType: 'UNIQUE'
            }
        );
    }

    function createUserIdIndex() {
        return queryInterface.addIndex(
            'user_identity',
            ['user_profile_id'],
            {
                indexName: 'idx_user_identity_user_profile_id',
                indexType: 'BTREE'
            }
        );
    }

    function createProviderUserIdIndex() {
        return queryInterface.addIndex(
            'user_identity',
            ['provider_user_id'],
            {
                indexName: 'idx_user_identity_provider_user_id',
                indexType: 'BTREE'
            }
        );
    }
}

function down(queryInterface, DataTypes) {

    return dropUserIdentityTable()
        .then(dropUserProfileTable);

    ///////////////////////////

    function dropUserIdentityTable() {
        return queryInterface.dropTable('user_identity');
    }

    function dropUserProfileTable() {
        return queryInterface.dropTable('user_profile');
    }
}