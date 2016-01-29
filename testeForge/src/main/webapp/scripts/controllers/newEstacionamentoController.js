
angular.module('testeForge').controller('NewEstacionamentoController', function ($scope, $location, locationParser, flash, EstacionamentoResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.estacionamento = $scope.estacionamento || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'Estacionamento cadastrado com sucesso!'});
            $location.path('/');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Alguma coisa de errado aconteceu. Tente novamente por favor.'}, true);
            }
        };
        EstacionamentoResource.save($scope.estacionamento, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/");
    };
    
    $scope.getLatLong = function() {
    	
    	var address = document.getElementById('endereco').value;
    	address = address + ", " + document.getElementById('n').value;
    	new google.maps.Geocoder().geocode( { 'address': address}, function(results, status) {
	      if (status == google.maps.GeocoderStatus.OK) 
	      {
	    	  $scope.estacionamento.latitude  = results[0].geometry.location.lat();
	    	  $scope.estacionamento.longitude = results[0].geometry.location.lng();
	    	  document.getElementById('latitude').value  = $scope.estacionamento.latitude;
	    	  document.getElementById('longitude').value = $scope.estacionamento.longitude;
	      } 
	      else 
	      {
	        alert("Não conseguimos pegar a localização exata de seu endereço. Verifique se digitou algo errado.");
	      }
	    });
    };
    
    $scope.$on('$viewContentLoaded', function(){
    	
    	initialize();
    	var placeSearch, autocomplete;
    	var componentForm = {
    	  route: 'long_name',
    	  locality: 'long_name',
    	  administrative_area_level_1: 'short_name',
    	  country: 'long_name',
    	  postal_code: 'short_name'
    	};
    	
    	function initialize() {
    	  autocomplete = new google.maps.places.Autocomplete((document.getElementById('endereco')), { types: ['geocode'] });
    	}
    });
});