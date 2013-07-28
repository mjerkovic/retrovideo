function ContactsController($scope, $http) {
    $http.get('/contacts').success(function(data) {
        $scope.contacts = data.contacts;
    });

    $scope.postIt = function() {
        alert("Heya!");
    }

}

function VideoController($scope, $http) {

    $scope.submit = function() {
        alert("Video Registered - " + $scope.data.title);
    }

}