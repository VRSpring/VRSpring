vr.controller('mainController', function ($scope, $http, $state, $dictionary) {
    $state.go('home');
    $scope.index = 'hot';
    $scope.category = $dictionary.category;
    $scope.select_type = 0;

    $scope.toggle = function (class_new) {
        $scope.index = class_new;
    };
    $scope.typeChange = function (id) {
        $scope.select_type = id;
    }
});
