var bodyParser = require('body-parser');
var express = require('express');
var helmet = require('helmet');
var path = require('path');
var q = require('q');
var Umzug = require('umzug');
var jwt = require('express-jwt');

var Logger = require('./api/util/logger.js');
var Properties = require('./api/util/properties.js');
var UserCacheMiddleware = require('./api/auth/userCacheMiddleware.js');
var ToSnakeCaseFilter = require('./api/util/case-conversion/toSnakeCaseFilter.js');

////////////////////////////////////

var app, server, database;

//Allow time to attach a debugger
setTimeout(function () {
    app = express();

    q.fcall(configureMiddleware)
        .then(connectDatabase)
        .then(migrateDatabase)
        .then(start)
        .catch(function (error) {
            Logger.error('Failed to start application')
                .exception(error)
                .build();
        });
// }, 5000);
});

////////////////////////////////////

function configureMiddleware() {
    var Routes = require('./api/routes.js');

    app.use(helmet({
        frameguard: {
            action: 'deny'
        }
    }));

    app.use(express.static(path.join(__dirname, 'resources', 'static')));

    //Check JWT
    app.use(jwt({
        secret: Properties.auth.secret,
        audience: Properties.auth.clientId
    }));

    //Handle any error from JWT
    app.use(function (err, req, res, next) {
        if (err.name === 'UnauthorizedError') {
            res.status(401).send('Unauthorized');
        }
    });

    //Load and cache profile
    app.use('/api', UserCacheMiddleware);

    app.use(bodyParser.json());
    app.use(ToSnakeCaseFilter);
    app.use('/api', Routes);
}

function connectDatabase() {
    database = require('./api/model/database.js');

    return database.sequelize
        .authenticate()
        .then(function() {
            Logger.info('Successfully connected to database').build();
        })
        .then(function() {
            return database.sequelize.sync();
        })
        .catch(function(error) {
            Logger.error('Failed to establish connection to database')
                .exception(error)
                .build();
            throw error;
        });
}

function migrateDatabase() {
    var umzugConfig = {
        storage: 'sequelize',
        storageOptions: {
            sequelize: database.sequelize
        },
        migrations: {
            path: 'src/resources/migrations',
            params: [database.sequelize.getQueryInterface(), database.sequelize.constructor, function() {
                throw new Error('Migration tried to use old style "done" callback. Please upgrade to "umzug" and return a promise instead.');
            }]
        }
    };

    var umzug = new Umzug(umzugConfig);

    var migrationTimer = require('./api/util/migrationTimer.js');

    umzug.on('migrating', function(migration) {
        migrationTimer.start(migration);
    });

    umzug.on('migrated', function(migration) {
        var duration = migrationTimer.stop(migration);
        Logger.info('Completed migration')
            .keyValue('script', migration)
            .keyValue('duration', duration)
            .build();
    });

    umzug.on('reverting', function(migration) {
        migrationTimer.stop(migration);
        Logger.warn('Reverting migration')
            .keyValue('script', migration)
            .build();
    });

    umzug.on('reverted', function(migration) {
        migrationTimer.stop(migration);
        Logger.warn('Reverted migration')
            .keyValue('script', migration)
            .build();
    });

    return umzug
        .up()
        .then(function(migrations) {
            Logger.info('Completed migrations')
                .keyValue('count', migrations.length)
                .build();
        });
}

function start() {
    server = app.listen(
        process.env.PORT || Properties.server.port,
        function () {
            Logger.info('Server started on port ' + server.address().port).build();
        }
    );

    server.on('error', function (error) {
        Logger.error('Server Error: ', error);
    });
}