var bodyParser = require('body-parser');
var express = require('express');
var helmet = require('helmet');
var path = require('path');
var passport = require('passport');
var BasicStrategy = require('passport-http').BasicStrategy;
var q = require('q');
var Umzug = require('umzug');

var Logger = require('./api/util/logger.js');
var Properties = require('./api/util/properties.js');
var Routes = require('./api/routes.js');
var LocalUsers = require('./api/util/localUsers.js');

////////////////////////////////////

var app, server, sequelizeInstance;

//Allow time to attach a debugger
setTimeout(function () {
    app = express();

    q.fcall(configureMiddleware)
        .then(configurePassport)
        .then(connectDatabase)
        .then(migrateDatabase)
        .then(start)
        .catch(function(error) {
            Logger.error('Failed to start application')
                .exception(error)
                .build();
        });
}, 5000);

////////////////////////////////////

function configureMiddleware() {
    app.use(passport.initialize());
    app.use('/api', passport.authenticate('basic', {session: false}));
    app.use(helmet({
        frameguard: {
            action: 'deny'
        }
    }));
    app.use(express.static(path.join(__dirname, 'resources/static')));
    app.use(bodyParser.json());
    app.use('/api', Routes);
}

function configurePassport() {
    passport.use(new BasicStrategy(
        function (username, password, done) {
            try {
                var user = LocalUsers.checkUser(username, password);
                return done(null, user);
            } catch(e) {
                return done(e);
            }
        }
    ));
}

function connectDatabase() {
    sequelizeInstance = require('./api/util/sequelizeInstance.js');

    return sequelizeInstance
        .authenticate()
        .then(function() {
            Logger.info('Successfully connected to database').build();
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
            sequelize: sequelizeInstance
        },
        migrations: {
            path: 'src/resources/migrations',
            params: [sequelizeInstance.getQueryInterface(), sequelizeInstance.constructor, function() {
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