vr.controller('homeController', function ($scope, $http, $timeout) {
    $http.post("/image/list", {}).success(function (data) {
        $scope.banners = data.banners;
        $scope.list = [];
        for (var i = 0; i < data.length; i = i + 2) {
            item = [];
            if (i < data.length) {
                item[0] = data[i];
            }
            i++;
            if (i < data.length) {
                item[1] = data[i];
            } else {
                item = null;
            }
            $scope.list.push(item);

        }
        console.log($scope.list);
    });
});
