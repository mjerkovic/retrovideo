function ContactsController($scope, $http) {
    $http.get('/contacts').success(function(data) {
        $scope.contacts = data.contacts;
    });

    $scope.postIt = function() {
        alert("Heya!");
    }

}

function NewVideoCtrl($scope, $http) {

    $scope.submit = function() {
        $http.post('/perform/addVideo', $scope.data).success(function() {

        });
    }

}

function ListVideosCtrl($scope, $http) {

    $http.get("/query").success(function(data) {
        $scope.videos = data;
    });

}