function ContactsController($scope, $http) {
    $http.get('/contacts').success(function(data) {
        $scope.contacts = data.contacts;
    });

}