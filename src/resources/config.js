/*
Global configuration here.
 */
var config = {
    auth: {
        domain: 'asudweeks.auth0.com',
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
    }
};

module.exports = config;