angular.module('testeForge').factory('EstacionamentoResource', function($resource){
    var resource = $resource('rest/estacionamentos/:EstacionamentoId',{EstacionamentoId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});