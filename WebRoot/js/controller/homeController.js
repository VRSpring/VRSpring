vr.controller('homeController', function ($scope, $http) {
    $scope.banners = [];
    $scope.list = [];


    $scope.$on('ngRepeatFinished', function () {
        jQuery(".swiper-container").swiper({
            loop: true,
            autoplay: 3000
        });
    });

    $http.post("image/list", {}).success(function (data) {
        $scope.banners = data.banners;
        var list = data.list;
        for (var i = 0; i < list.length; i++) {
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
                item = {};
            }
            $scope.list.push(item);
        }
    });
});
