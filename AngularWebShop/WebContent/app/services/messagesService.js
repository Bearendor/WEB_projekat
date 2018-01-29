webShop.factory('messagesFactory', function($http) {
	
	var factory = {};
	factory.getMessages = function() {
		return $http.get('/AngularWebShop/rest/poruke/getJustMessages');
	};
	
	factory.addMessage = function(msg) {
		return $http.post('/AngularWebShop/rest/poruke/addMessage', msg);	
	};
	
	factory.changeMessage = function(msg) {
		return $http.post('/AngularWebShop/rest/poruke/changeMessage', msg);	
	};
		
	return factory;
	
});