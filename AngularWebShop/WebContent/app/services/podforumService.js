webShop.factory('podforumFactory', function($http) {
	
	var factory = {};
	factory.getPodforumi = function() {
		return $http.get('/AngularWebShop/rest/podforumi/getJustPodforumi');
	};
	
	factory.dodajPodforum = function(podforum) {
		return $http.post('/AngularWebShop/rest/podforumi/dodajPodforum', podforum);
		
	};
	
	factory.obrisiPodforum = function(naziv) {
		return $http.post('/AngularWebShop/rest/podforumi/obrisiPodforum', naziv);
	};
	
	return factory;
	
});