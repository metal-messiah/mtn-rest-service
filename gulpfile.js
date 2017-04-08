var gulp = require('gulp');
var angularFileSort = require('gulp-angular-filesort');
var browserSync = require('browser-sync').create();
var concat = require('gulp-concat');
var concatCss = require('gulp-concat-css');
var del = require('del');
var filter = require('gulp-filter');
var gulpIf = require('gulp-if');
var inject = require('gulp-inject');
var mbf = require('main-bower-files');
var path = require('path');
var q = require('q');
var runSequence = require('run-sequence');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var _ = require('lodash');

var sourceWebPath = './src/web';
var sourceHtmlPath = './src/web/template';
var sourceImagesPath = './src/web/images';
var sourceJsPath = './src/web/js';
var sourceScssPath = './src/web/sass';
var targetPath = './src/resources/static';

/***
 *** Simple tasks
 ***/

gulp.task('clean-static', cleanStatic);
gulp.task('compile-sass', compileSass);
gulp.task('compile-js', compileJs);
gulp.task('copy-html', copyHtml);
gulp.task('copy-dependencies', copyDependencies);
gulp.task('copy-images', copyImages);
gulp.task('init-browser-sync', initBrowserSync);
gulp.task('inject-js-dependencies', injectJsDependencies);
gulp.task('inject-css-dependencies', injectCssDependencies);

/***
 *** Composite tasks
 ***/

gulp.task('build-index', function (callback) {
    runSequence(
        'copy-html',
        'inject-js-dependencies',
        'inject-css-dependencies',
        callback
    );
});

gulp.task('watch', function () {
    gulp.watch(sourceJsPath + '/**/*.js', ['compile-js', browserSync.reload]);
    gulp.watch(sourceScssPath + '/**/*.scss', ['compile-sass', browserSync.reload]);
    gulp.watch(sourceHtmlPath + '/**/*.html', ['copy-html', browserSync.reload]);
    gulp.watch(sourceWebPath + '/index.html', ['build-index', browserSync.reload]);
});

gulp.task('build', function (callback) {
    runSequence(
        'clean-static',
        ['copy-html', 'copy-images', 'copy-dependencies'],
        'inject-js-dependencies',
        'inject-css-dependencies',
        ['compile-sass', 'compile-js'],
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

////////////////////////////////////////

/**
 * Delete the static folder and all its contents.
 */
function cleanStatic() {
    return q(del(targetPath));
}

/**
 * Compile SASS into CSS, concat together, and put concat'd file in static folder.
 */
function compileSass() {
    var deferred = q.defer();

    gulp.src(sourceScssPath + '/**/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(concatCss('mtn.css'))
        .pipe(gulp.dest(targetPath + '/styles'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Concat all JS files together and put the concat'd file in the static folder.
 */
function compileJs() {
    var deferred = q.defer();

    var f = filter(['**', '!**/*.spec.js'], {restore: true});

    gulp.src(sourceJsPath + '/**/*.js')
        .pipe(f)
        .pipe(angularFileSort())
        .pipe(uglify({
            beautify: true,
            mangle: false,
            compress: false
        }))
        .pipe(concat('mtn.js'))
        .pipe(gulp.dest(targetPath + '/scripts'))
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
        .pipe(gulp.dest(targetPath + '/lib'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Copy HTML files to static folder.
 */
function copyHtml() {
    var deferred = q.defer();

    gulp.src(sourceWebPath + '/**/*.html')
        .pipe(gulp.dest(targetPath))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred;
}

/**
 * Copy images to static folder.
 */
function copyImages() {
    var deferred = q.defer();

    gulp.src(sourceImagesPath + '/**')
        .pipe(gulp.dest(targetPath + '/images'))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Inject <script> tags into index.html for bower dependencies.
 */
function injectJsDependencies() {
    var deferred = q.defer();

    var f = filter(['**', '!**/mtn.js'], {restore: true});

    var target = gulp.src(targetPath + '/index.html');
    var sources = gulp.src(targetPath + '/**/*.js')
        .pipe(f)
        .pipe(angularFileSort());

    target.pipe(inject(sources, {relative: true}))
        .pipe(gulp.dest(targetPath))
        .on('end', deferred.resolve)
        .on('error', deferred.reject);

    return deferred.promise;
}

/**
 * Inject <link> tags into index.html for bower dependencies.
 */
function injectCssDependencies() {
    var deferred = q.defer();

    var target = gulp.src(targetPath + '/index.html');
    var sources = gulp.src(targetPath + '/**/*.css');

    target.pipe(inject(sources, {relative: true}))
        .pipe(gulp.dest(targetPath))
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
        port: 3030,
        proxy: 'localhost:3000',
        serveStatic: ['./src/resources/static']
    };

    browserSync.init(config, deferred.resolve);

    return deferred.promise;
}