/**
 * 行程检核-路由
 * 韩春阳
 * 2016-01-20
 */
vr.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

    $httpProvider.defaults.transformRequest = function (obj) {
        var str = [];
        for (var p in obj) {
            if (p === undefined || p === null || p === '') {
                continue;
            }
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
        return str.join("&");
    };

    $httpProvider.defaults.headers.post = {
        'Content-Type': 'application/x-www-form-urlencoded'
    };
    $urlRouterProvider.when("", "/home");
    $stateProvider.state('home', {
        url: '/home',
        templateUrl: 'js/template/homeTemplate.html',
        controller: 'homeController'
    }).state('channel', {
        url: '/channel',
        templateUrl: 'js/template/channelTemplate.html',
        controller: 'channelController'
    }).state('about', {
        url: '/about',
        templateUrl: 'js/template/aboutTemplate.html',
        controller: 'aboutController'
    }).state('upload', {
        url: '/upload',
        templateUrl: 'js/template/uploadTemplate.html',
        controller: 'uploadController'
    }).state('profile', {
        url: '/profile',
        templateUrl: 'js/template/profileTemplate.html',
        controller: 'profileController'
    });
});
