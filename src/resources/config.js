/*
Global configuration here.
 */
var config = {
    auth: {
        secret: 'cfI8g1OJ0QX8J-edJjypVCXGA6ef5dJMSG0OAiWGJ7Lzs9st-nsRhO0ccDXcAYEU',
        clientId: 'FArOoQuRqPT1MZFsNE9qnxeykHp48cIO'
    },
    database: {
        dialect: 'postgres',
        maxPoolSize: 50,
        minPoolSize: 1,
        maxIdleTimeMs: 60000,
        showSql: false
    },
    server: {
        port: 3000
    },
    users: [
        {
            username: 'mtn_rest_service',
            password: 'R3t@1lAdv1s0rs'
        }
    ]
};

module.exports = config;