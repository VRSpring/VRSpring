vr.controller('uploadController', function ($scope, $http, $state, $dictionary) {
    $scope.PICTURES_MAX = 2;
    $scope.pictures = [];
    $scope.files = []
    $scope.show_add = true;
    $scope.picture_select = function ($ele) {
        if ($ele.files) {
            var add_file = $ele.files[0];
            var allowExtention = ".jpg,.bmp,.gif,.png";
            var extention = add_file.name.substring(add_file.name.lastIndexOf(".") + 1).toLowerCase();
            if (allowExtention.indexOf(extention) > -1) {
                $scope.pictures.push(window.URL.createObjectURL(add_file));
                $scope.files.push(add_file);
                $ele.files = null;
                if ($scope.pictures.length >= $scope.PICTURES_MAX) {
                    $scope.show_add = false;
                }
                $scope.$apply();
            }
        }
    };
    $scope.upload = function () {
        var formdata = new FormData();
        formdata.append('text', $scope.uploadData.remark);
        for (var i = 0; i < $scope.files.length; i++) {
            formdata.append('file[]', $scope.files[i]);
        }
        $http({
            method: 'POST',
            url: "/upload",
            data: formdata,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function (response) {
            //上传成功的操作
            alert("uplaod success");
        });

    }

    $scope.picture_click = function ($index) {
        $scope.pictures.splice($index, 1);
        $scope.files.splice($index, 1);
    }
});
