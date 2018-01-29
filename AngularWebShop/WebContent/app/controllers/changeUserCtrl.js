webShop.controller('changeUserController', function($scope, $rootScope, $location, userFactory) {
	$scope.users = userFactory;
	
	$scope.users = $rootScope.users;
    
	function init() {
		userFactory.getUsers().success(function (data) {
        	$scope.users = data;
		});
    }
    
    $scope.promeniUlogu = function(user) {
    	console.log($rootScope.users);
    	console.log($scope.users);
    	
    	userFactory.promeniUlogu(user).success(function() {
    		init();
    	});
    }
    
});