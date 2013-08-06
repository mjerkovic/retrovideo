angular.module('retrovideo', ['$strap.directives']).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/newVideo', {templateUrl: 'new-video.html',   controller: NewVideoCtrl}).
            when('/videos', {templateUrl: 'list-video.html', controller: ListVideosCtrl}).
            otherwise({redirectTo: '/videos'});
    }]);
