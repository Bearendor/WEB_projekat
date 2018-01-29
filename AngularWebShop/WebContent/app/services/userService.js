webShop.factory('userFactory', function($http) {
	
	var factory = {};
	factory.getUsers = function() {
		return $http.get('/AngularWebShop/rest/korisnici/getJustUsers');
	};
	
	factory.addUser = function(user) {
		return $http.post('/AngularWebShop/rest/korisnici/registerUser', user);
	};
	
	factory.promeniUlogu = function(user) {
		return $http.post('/AngularWebShop/rest/korisnici/promeniUlogu', user);
	};
	
	
		
	return factory;
	
});