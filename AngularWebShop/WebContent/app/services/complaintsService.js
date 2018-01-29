webShop.factory('complaintsFactory', function($http) {
	
	var factory = {};
	factory.getComplaints = function() {
		return $http.get('/AngularWebShop/rest/zalbe/getJustComplaints');
	};
	
	factory.addComplaint = function(complaint) {
		return $http.post('/AngularWebShop/rest/zalbe/addComplaint', complaint);
	};	
	
	factory.deleteComplaint = function(complaint) {
		return $http.post('/AngularWebShop/rest/zalbe/deleteComplaint', complaint);	
	};
	
	return factory;
	
});