vr.controller('uploadController', function ($scope, $http, $state, $dictionary) {
    $scope.save = function() {
        var fd = new FormData();
        fd.append('file', $scope.myFile);
        fd.append('desc',  $scope.desc);
        $http({
            method:'POST',
            url:"image/upload",
            data: fd,
            headers: {'Content-Type':undefined},
            transformRequest: angular.identity
        })
            .success( function ( response )
            {
                //上传成功的操作
                alert("uplaod success"+$scope.desc+"--"+file);
            });

    }
})

