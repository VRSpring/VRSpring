vr.directive("swiperContainer", function () {
    return {
        restrict: 'E',
        scope: {
            list: '='
        },
        replace: true,
        transclude: true,
        template: '<div><div ng-transclude>transclude</div></div>',
        link: function (scope, el, attrs) {
            scope.$watch('list', function (value, oldValue, scope) {
                if (value) {
                    jQuery(el).find(".swiper-container").swiper({
                        loop: true,
                        autoplay: 3000
                    });
                }
            }, true);
        }
    }
});