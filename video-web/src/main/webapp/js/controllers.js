function NewVideoCtrl($scope, $http, $location) {

    $scope.submit = function() {
        $http.post('/perform/addVideo', $scope.data).success(function() {
            $location.path("/videos");
        });
    }

}

function ListVideosCtrl($scope, $http) {

    $http.get("/query").success(function(data) {
        $scope.videos = data;
    });

}