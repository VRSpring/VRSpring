vr.controller('homeController', function ($scope, $http, $timeout) {
    $scope.banners = [];
    $scope.list = [];
    $http.post("/image/list", {}).success(function (data) {
        console.log(data);
        $scope.banners = data.banners;
        var list = data.list;
        for (var i = 0; i < list.length; i = i + 2) {
            item = [];
            if (i < list.length) {
                item[0] = list[i];
                item[0]['view'] = item[0].dir + "?tourxml=" + item[0].tourxml + "&title=" + item[0].name;
            }
            i++;
            if (i < list.length) {
                item[1] = list[i];
                item[1]['view'] = item[1].dir + "?tourxml=" + item[1].tourxml + "&title=" + item[1].name;
            } else {
                item = null;
            }
            $scope.list.push(item);
        }
    });
});
