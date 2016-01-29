'use strict';

angular.module('testeForge',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'landing.html',controller:'LandingPageController'})
      .when('/cadastrar',{templateUrl:'views/Estacionamento/detail.html',controller:'NewEstacionamentoController'})
      ;
  }])
  .controller('EstacionamentoController', function NavController($scope, $location) {
    
  })
  .controller('LandingPageController', function LandingPageController($scope) {
	  
	  $scope.$on('$viewContentLoaded', function(){
		  /***************** Waypoints ******************/

			$('.wp1').waypoint(function() {
				$('.wp1').addClass('animated fadeInLeft');
			}, {
				offset: '75%'
			});
			$('.wp2').waypoint(function() {
				$('.wp2').addClass('animated fadeInDown');
			}, {
				offset: '75%'
			});
			$('.wp3').waypoint(function() {
				$('.wp3').addClass('animated bounceInDown');
			}, {
				offset: '75%'
			});
			$('.wp4').waypoint(function() {
				$('.wp4').addClass('animated fadeInDown');
			}, {
				offset: '75%'
			});

			/***************** Flickity ******************/

			$('#featuresSlider').flickity({
				cellAlign: 'left',
				prevNextButtons: false,
				contain: true
			});

			$('#showcaseSlider').flickity({
				cellAlign: 'left',
				contain: true,
				prevNextButtons: false,
				imagesLoaded: true
			});
	  });
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
