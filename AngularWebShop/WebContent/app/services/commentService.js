webShop.factory('commentFactory', function($http) {
	
	var factory = {};
	factory.getComments = function() {
		return $http.get('/AngularWebShop/rest/komentari/getJustComments');
	};
	
	factory.addComment = function(comment) {
		return $http.post('/AngularWebShop/rest/komentari/addComment', comment);	
	};
	
	factory.edit = function(comment) {
		return $http.post('/AngularWebShop/rest/komentari/editComment', comment)
	};
	
	factory.promeniGlas = function(comm) {
		return $http.post('/AngularWebShop/rest/komentari/promeniGlas', comm);
	};
	
	factory.reply = function(data) {
		return $http.post('/AngularWebShop/rest/komentari/replyComment', data);	
	};
	
	factory.deleteComment = function(comment) {
		return $http.post('/AngularWebShop/rest/komentari/deleteComment', comment);	
	};
		
	return factory;
	
});