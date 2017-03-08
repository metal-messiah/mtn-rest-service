var bodyParser = require('body-parser');
var express = require('express');
var helmet = require('helmet');
var path = require('path');
var passport = require('passport');
var BasicStrategy = require('passport-http').BasicStrategy;

var Logger = require('./api/util/logger.js');
var Properties = require('./api/util/properties.js');
var Routes = require('./api/routes.js');
var LocalUsers = require('./api/util/localUsers.js');

////////////////////////////////////

var app, server;

//Allow time to attach a debugger
setTimeout(function () {
    app = express();
    configureMiddleware();
    configurePassport();
    start();
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

function start() {
    var SequelizeInstance = require('./api/util/sequelizeInstance.js');

    //Connect to database, then start server
    SequelizeInstance
        .authenticate()
        .then(function() {
            Logger.info('Successfully connected to database').build();

            server = app.listen(
                process.env.PORT || Properties.server.port,
                function () {
                    Logger.info('Server started on port ' + server.address().port).build();
                }
            );

            server.on('error', function (error) {
                Logger.error('Server Error: ', error);
            });
        })
        .catch(function(error) {
            Logger.error('Failed to establish connection to database')
                .exception(error)
                .build();
        });
}