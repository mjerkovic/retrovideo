var app = angular.module('retrovideo', ['$strap.directives']);

app.controller('NewVideoCtrl', function($scope, $http, $location) {

    $scope.submit = function() {
        $http.post('/video', $scope.data).success(function() {
            $location.path("/videos");
        });
    }


});

app.controller('ListVideosCtrl', function($scope, $http) {

    $http.get("/video").success(function(data) {
        $scope.videos = data;
    });

    $http.get("/employee/current").success(function(data) {
        $scope.employee = {
            firstname: data.firstname,
            lastname: data.lastname
        };
    });


});

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/newVideo', {templateUrl: 'new-video.html',   controller: 'NewVideoCtrl'}).
        when('/videos', {templateUrl: 'list-video.html', controller: 'ListVideosCtrl'}).
        otherwise({redirectTo: '/videos'});
}]);