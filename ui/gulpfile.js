var gulp = require('gulp');
var concat = require('gulp-concat');
var header = require('gulp-header');
var connect = require("gulp-connect");
var less = require("gulp-less");
var autoprefixer = require('gulp-autoprefixer');
var ejs = require("gulp-ejs");
var uglify = require('gulp-uglify');
var ext_replace = require('gulp-ext-replace');
var cssmin = require('gulp-cssmin');

gulp.task('js', function (cb) {
    gulp.src([
            './src/js/city-data.js',
            './src/js/city-picker.js'
        ])
        .pipe(concat({path: 'city-picker.js'}))
        .pipe(gulp.dest('./dist/demos/js/'))
        .on("end", end);


});

gulp.task('uglify', ["js"], function () {
    return gulp.src(['./dist/js/*.js', '!./dist/js/*.min.js'])
        .pipe(uglify({
            preserveComments: "license"
        }))
        .pipe(ext_replace('.min.js'))
        .pipe(gulp.dest('./dist/js'));
});

gulp.task('less', function () {
    return gulp.src(['./less/vr.less'])
        .pipe(less())
        .pipe(autoprefixer())
        .pipe(gulp.dest('../WebRoot/css/'));
});


gulp.task('watch', function () {
    gulp.watch('./less/vr.less', ['less']);
});

gulp.task('server', function () {
    connect.server({
        port: 8080,
        livereload: true
    });
});


gulp.task("default", ['less', 'watch']);