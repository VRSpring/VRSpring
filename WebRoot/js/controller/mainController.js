vr.controller('mainController', function ($rootScope, $scope, $http, $state, $stateParams, $dictionary) {
    $state.go('home');
    $scope.index = 'hot';
    $scope.category = $dictionary.category;
    $scope.select_type = '1';

    $scope.toggle = function (class_new) {
        $scope.index = class_new;
    };
    $scope.typeChange = function (id) {
        $scope.select_type = id;
    }
    $scope.state = 'home';
    $rootScope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (toState.name == 'home' || toState.name == 'upload' || toState.name == 'profile') {
                $scope.state = toState.name;
            } else {
                $scope.state = 'other';
            }

        });
});
