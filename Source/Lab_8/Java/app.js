// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('first', ['ionic','ui.router'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})


.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
  $urlRouterProvider.otherwise('/')

  $stateProvider.state('second', {
    url: '/second',
    templateUrl: 'second.html',
      controller:'getweather'
  })
})

 .controller('details', function ($scope, $ionicModal, $location, $state, $http) {
    $scope.getdata = function () {
      $location.path('/second');
     $state.go('second');  
        $scope.text='Test case';
    };
    

    city = document.getElementById('CityName').value;
        $scope.city1='Name: '+city;
    var weather = { };
    console.log('start')
    $http.get('http://api.openweathermap.org/data/2.5/weather?q='+city+'&appid=a2c1cdbc7a2e370118610010dfafbaac').then(function(data) {
                       console.log("dhvdkbkf"+data);
       // $scope.x=data.data.main;
        $scope.des='Description :'+data.data.weather[0].description;
        var temp=data.data.weather[0].description;
        console.log(temp);
        var sec=data.main;
        console.log(sec);
        $scope.temperature='Temperature is :'+data.data.main.temp;
        console.log(temperature);
        $scope.temp_min='Min Temp: '+data.data.main.temp_min;
        console.log(temp_min);
        $scope.temp_max='Max temp: '+data.data.main.temp_max;
        console.log(temp_max);
        $scope.hum='Humidity is: '+data.data.main.humididty;
        console.log(hum);
        $scope.temp='Min Temp is: '+data.data.main.temp;
        $scope.desc='Weather Details: '+data.data.weather[0].description;
                    //console.log(x);
                                       //         $scope.main=data.main;
        //console.log(main);
                                                //$scope.temp = data.main.temp;
                                            })
    
    $http.get('http://maps.googleapis.com/maps/api/geocode/json?address='+city+'&sensor=true').then(function(data2) {
                       console.log(data2);
        //$scope.x=data2.data.results[0];
        $scope.cityName= 'Location: '+data2.data.results[0].formatted_address;
       $scope.lat='Latitude: '+data2.data.results[0].geometry.location.lat;
        var latit=data2.data.results[0].geometry.location.lat;
        var longit=data2.data.results[0].geometry.location.lng;
    $scope.lng='longitude: '+data2.data.results[0].geometry.location.lng;
        $scope.placeid='Place ID for '+city+' is: '+data2.data.results[0].place_id;
        //<img ng-src="http://www.gravatar.com/avatar/{{hash}}" alt="Description" />
       // var img_url="http://maps.googleapis.com/maps/api/staticmap?center="latit","longit"+100&zoom=6&size=800x400&sensor=false";
        //$scope.map=
       // var img="<img ng-src='"+img_url+"'/>";
        //$scope.map=img;
       })
    
    
    
})
