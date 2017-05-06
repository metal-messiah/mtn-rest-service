var gulp = require('gulp');
var angularFileSort = require('gulp-angular-filesort');
var browserSync = require('browser-sync').create();
var concat = require('gulp-concat');
var concatCss = require('gulp-concat-css');
var del = require('del');
var filter = require('gulp-filter');
var gulpIf = require('gulp-if');
var inject = require('gulp-inject');
var sass = require('gulp-sass');
var mbf = require('main-bower-files');
var path = require('path');
var q = require('q');
var runSequence = require('run-sequence');
var uglify = require('gulp-uglify');
var _ = require('lodash');
var watch = require('gulp-watch');

var staticPath = './src/main/resources/static';

//Find production arg if present
var env = _.find(process.argv, function (arg) {
    return arg === 'production';
});

//Find proxyPort arg if present
var proxyPort = _.find(process.argv, function (arg) {
    return arg.indexOf('--proxy-port') !== -1;
});
if (proxyPort) {
    proxyPort = Number(proxyPort.split('=')[1]);
}

var options = {
    env: env || 'local',
    proxyPort: proxyPort || 8080
};

console.log(proxyPort);

/***
 *** Simple tasks
 ***/

gulp.task('browser-sync-reload', browserSyncReload);
gulp.task('build-index', buildIndex);
gulp.task('clean-static', cleanStatic);
gulp.task('compile-custom-sass', compileCustomSass);
gulp.task('compile-main-custom-js', compileMainCustomJs);
gulp.task('copy-html', copyHtml);
gulp.task('copy-css', copyCss);
gulp.task('copy-dependencies', copyDependencies);
gulp.task('copy-images', copyImages);
gulp.task('init-browser-sync', initBrowserSync);

/***
 *** Composite tasks
 ***/

gulp.task('watch', function () {
    //Watch core JS
    watch('./ng-app/**/*.js', function () {
        runSequence(
            'compile-main-custom-js',
            'browser-sync-reload'
        );
    });

    //Watch core SASS
    watch('./ng-app/**/*.scss', function () {
        runSequence(
            'compile-custom-sass',
            'browser-sync-reload'
        );
    });

    //Watch core HTML
    watch('./ng-app/**/*.html', function () {
        runSequence(
            'copy-html',
            'browser-sync-reload'
        );
    });

    //Watch index.html
    watch('./ng-app/index.html', function () {
        runSequence(
            'build-index',
            'browser-sync-reload'
        );
    });
});

gulp.task('build', function (callback) {
    runSequence(
        'clean-static',
        ['copy-html', 'copy-css', 'copy-images', 'copy-dependencies'],
        ['compile-custom-sass', 'compile-main-custom-js'],
        ['build-index'],
        callback
    );
});

gulp.task('dev', function (callback) {
    runSequence(
        'build',
        'watch',
        'init-browser-sync',
        callback
    );
});

///////////////////////////////////////

function browserSyncReload() {
    browserSync.reload();
}

/**
 * Copy index.html to target directory after injecting <link> and <script> tags
 */
function buildIndex() {
    var deferred = q.defer();

    var bowerJsFilter = filter(['**', '!**/mtn*.js'], {restore: true});
    var cssFilter = filter(['**', '**/*.css', '!**/mtn*.css'], {restore: true});

    gulp.src('./ng-app/index.html')
        .pipe(gulp.dest(staticPath))
        .pipe(inject(gulp.src(staticPath + '/**/*.js')
            .pipe(bowerJsFilter)
            .pipe(angularFileSort()), {relative: true}))
        .pipe(inject(gulp.src(staticPath + '/**/*.css')
            .pipe(cssFilter), {relative: true}))
        .pipe(inject(gulp.src(staticPath + '/**/mtn*.js'), {name: 'mtn', relative: true}))
        .pipe(inject(gulp.src(staticPath + '/**/mtn*.css'), {name: 'mtn', relative: true}))
        .pipe(gulp.dest(staticPath))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Delete the static folder and all its contents.
 */
function cleanStatic() {
    return q(del(staticPath));
}

/**
 * Compile SASS into CSS, concat together, and put concat'd file in static folder.
 */
function compileCustomSass() {
    var deferred = q.defer();

    gulp.src('./ng-app/**/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(concatCss('mtn.css'))
        .pipe(gulp.dest(staticPath + '/styles'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Concat all non-canary custom JS files together. If env === production, uglify
 * and compress them as well. Finally, put the concat'd file in the static folder.
 */
function compileMainCustomJs() {
    var deferred = q.defer();

    var f = filter(['**', '!**/*.spec.js'], {restore: true});

    gulp.src('./ng-app/**/*.js')
        .pipe(f)
        .pipe(angularFileSort())
        .pipe(concat('mtn.js'))
        .pipe(gulp.dest(staticPath + '/scripts'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Copy all CSS files to static folder.
 */
function copyCss() {
    var deferred = q.defer();

    gulp.src('./ng-app/**/*.css')
        .pipe(gulp.dest(staticPath + '/styles'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Copy all bower dependency files to static folder.
 */
function copyDependencies() {
    var deferred = q.defer();

    gulp.src(mbf())
        .pipe(gulp.dest(staticPath + '/lib'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Copy HTML files to static folder.
 */
function copyHtml() {
    var deferred = q.defer();

    var f = filter(['**', '!**/index.html'], {restore: true});

    gulp.src('./ng-app/**/*.html')
        .pipe(f)
        .pipe(gulp.dest(staticPath))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred;
}

/**
 * Copy images to static folder.
 */
function copyImages() {
    var deferred = q.defer();

    gulp.src('./ng-app/images/**')
        .pipe(gulp.dest(staticPath + '/images'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Starts the BrowserSync server
 */
function initBrowserSync() {
    var deferred = q.defer();

    var config = {
        proxy: {
            target: 'localhost:' + options.proxyPort,
            ws: true
        },
        serveStatic: ['./src/main/resources/static']
    };

    browserSync.init(config, deferred.resolve);

    return deferred.promise;
}