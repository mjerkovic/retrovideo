function NewVideoCtrl($scope, $http, $location) {

    $scope.submit = function() {
        $http.post('/video', $scope.data).success(function() {
            $location.path("/videos");
        });
    }

}

function ListVideosCtrl($scope, $http) {

    $http.get("/video").success(function(data) {
        $scope.videos = data;
    });

}