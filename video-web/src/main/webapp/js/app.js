var app = angular.module('retrovideo', ['ui.bootstrap', '$strap.directives']);

app.directive('fileupload', function () {
    return {
        restrict: 'E',

        template: '<span>' +
            '<label for="displayImg">Upload image</label>' +
            '<input type="file" id="displayImg" onchange="angular.element(this).scope().setFile(this)">' +
            '<button class="btn btn-primary" ng-click="uploadFile()">Save</button>' +
            '</span>',

        replace: true,

        controller: function ($scope) {

            $scope.setFile = function (elem) {
                $scope.inputField = elem;
                $scope.file = elem.files[0];
            };

            $scope.uploadFile = function () {
                var fd = new FormData(), xhr = new XMLHttpRequest();
                fd.append("file", $scope.file);
                xhr.open("POST", "/upload");
                xhr.send(fd);
                $scope.inputField.value = "";
            };
        }
    };
});

app.controller('NewVideoCtrl', function($scope, $http, $location) {

    $scope.submit = function() {
        $http.post('/video', $scope.data).success(function() {
            $location.path("/videos");
        });
    }


});

app.controller('ListVideosCtrl', function($scope, $http) {

    $scope.qtyToAdd = {};

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
        var config = {
            method: 'GET',
            url: '/video/page/' + page,
            params: { 'searchKey': $scope.videoList.searchKey }
        };
        $http(config).success(function(data) {
            $scope.currentPage = page;
            $scope.videoList = data;
        });
    }

    $scope.searchByType = function() {
        $http.get("/video/stats/" + $scope.category).success(function(data) {
            $scope.facets = [];
            for (var key in data.facets) {
                if (data.facets.hasOwnProperty(key)) {
                    $scope.facets.push({ "name": key, "count": data.facets[key] });
                }
            }
        });
    }

    $scope.addStock = function(videoId) {
        var qty = $scope.qtyToAdd[videoId];
        $http.post("/stock/add/" + videoId, qty)
            .success(function(data) {
                $scope.videoList.videos.forEach(function(video) {
                    $scope.newPage($scope.currentPage);
                });
            });
    }

    $http.get("/video").success(function(data) {
        $scope.videoList = data;
    });
    $scope.currentPage = 1;

    $scope.category = "country";
    $scope.searchByType();
/*
    $http.get("/video/stats/country").success(function(data) {
        $scope.facets = [];
        for (var key in data.facets) {
            if (data.facets.hasOwnProperty(key)) {
                $scope.facets.push({ "name": key, "count": data.facets[key] });
            }
        }
    });
*/

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

    $scope.uploadFile = function () {
        return $http({
                method: "POST",
                url: "/upload",
                headers: {
                    "Content - Type": undefined
                },
                data: $scope.fileToUpload,
                transformRequest: function (data) {
                    return data;
                }
            }
        );
    }

});

app.controller('AccountCtrl', function($scope, $http) {

    $scope.createAccount = function() {
        alert($scope.account);
    }

});


app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/newVideo', {templateUrl: 'new-video.html',   controller: 'NewVideoCtrl'}).
        when('/videos', {templateUrl: 'list-video.html', controller: 'ListVideosCtrl'}).
        when('/inventory', {templateUrl: 'inventory.html', controller: 'InventoryCtrl'}).
        when('/accounts', {templateUrl: 'new-account.html', controller: 'AccountCtrl'}).
        otherwise({redirectTo: '/videos'});
}]);