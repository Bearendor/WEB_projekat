webShop.factory('themeFactory', function($http) {
	
	var factory = {};
	factory.getThemes = function() {
		return $http.get('/AngularWebShop/rest/teme/getJustThemes');
	};
	
	factory.promeniGlas = function(tema) {
		return $http.post('/AngularWebShop/rest/teme/promeniGlas', tema);
	};
	
	factory.createTheme = function(tema) {
		return $http.post('/AngularWebShop/rest/teme/createTheme', tema);
	};
	
	factory.deleteTheme = function(tema) {
		return $http.post('/AngularWebShop/rest/teme/deleteTheme', tema);
	};
		
	return factory;
	
});