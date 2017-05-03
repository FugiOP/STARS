  var config = {
    apiKey: "AIzaSyArDMbj8qBsWMYbatwX4hnXXhZROrQBDdg",
    authDomain: "stars-61dcc.firebaseapp.com",
    databaseURL: "https://stars-61dcc.firebaseio.com",
    projectId: "stars-61dcc",
    storageBucket: "stars-61dcc.appspot.com",
    messagingSenderId: "901145964953"
  };
  firebase.initializeApp(config);

angular.module('fbApp', ['firebase', 'angularMoment'])
    .controller('SyncController', ['$scope', '$firebaseArray', function ($scope, $firebaseArray) {

        let ref = firebase.database().ref().child("Students")

        $scope.students = $firebaseArray(ref)        


}])