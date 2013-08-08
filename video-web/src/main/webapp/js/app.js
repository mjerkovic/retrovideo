var app = angular.module('retrovideo', ['ui.bootstrap', '$strap.directives']);

app.controller('NewVideoCtrl', function($scope, $http, $location) {

    $scope.submit = function() {
        $http.post('/video', $scope.data).success(function() {
            $location.path("/videos");
        });
    }


});

app.controller('ListVideosCtrl', function($scope, $http) {

    $scope.getNumber = function(num) {
        return new Array(num);
    }

    $http.get("/employee/current").success(function(data) {
        $scope.employee = {
            firstname: data.firstname,
            lastname: data.lastname
        };
    });

    $scope.newPage = function(page) {
        $http.get("/video/page/"+(page-1)).success(function(data) {
            $scope.videoList = data;
        });
    }

    $scope.newPage(1);

});

app.controller('InventoryCtrl', function($scope, $http) {

    $scope.stock = [];

    $http.get("/video/page/0").success(function(data) {
        $scope.videos = data;
    });

    $scope.submit = function() {
        var selectedVideo = $scope.videos.filter(function(video) {
            return video.videoId == $scope.newstock.title;
        });
        $scope.stock.push({ title: selectedVideo[0].title, quantity: $scope.newstock.quantity });
    }

});


app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/newVideo', {templateUrl: 'new-video.html',   controller: 'NewVideoCtrl'}).
        when('/videos', {templateUrl: 'list-video.html', controller: 'ListVideosCtrl'}).
        when('/inventory', {templateUrl: 'inventory.html', controller: 'InventoryCtrl'}).
        otherwise({redirectTo: '/videos'});
}]);