/*
Global configuration here.
 */
var config = {
    database: {
        dialect: 'postgres',
        maxPoolSize: 50,
        minPoolSize: 1,
        maxIdleTimeMs: 60000
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