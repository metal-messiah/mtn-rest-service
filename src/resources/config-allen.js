/*
Local-specific configuration here
 */
var config = {
    auth: {
        callbackUrl: 'http://localhost:3000'
    },
    database: {
        name: 'mtn',
        host: 'localhost',
        port: 5433,
        username: 'mtn-service-user',
        password: '4ccb15ce-0453-11e7-93ae-92361f002671'
    }
};

module.exports = config;