

angular.module('testeForge').controller('EditEstacionamentoController', function($scope, $routeParams, $location, flash, EstacionamentoResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.estacionamento = new EstacionamentoResource(self.original);
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The estacionamento could not be found.'});
            $location.path("/Estacionamentos");
        };
        EstacionamentoResource.get({EstacionamentoId:$routeParams.EstacionamentoId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.estacionamento);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The estacionamento was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.estacionamento.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Estacionamentos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The estacionamento was deleted.'});
            $location.path("/Estacionamentos");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.estacionamento.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});