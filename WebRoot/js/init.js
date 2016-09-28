vr = angular.module('com.vrspring.vr', ['ui.router']);

/**
 * 公共常量定义
 */
vr.factory('$dictionary', function ($http) {
    var dictionary = {};

    dictionary.category = [
        {'id': '1', 'type': '热门'},
        {'id': '2', 'type': '美女'},
        {'id': '3', 'type': '风景'},
        {'id': '4', 'type': '自然'},
        {'id': '5', 'type': '婚庆'},
        {'id': '6', 'type': '聚会'},
        {'id': '7', 'type': '汽车'},
        {'id': '8', 'type': '房产'},
        {'id': '9', 'type': '游戏画面'},
        {'id': '10', 'type': '高科技'},
        {'id': '11', 'type': '美景'},
        {'id': '12', 'type': '都市'}
    ];

    return dictionary;
})
