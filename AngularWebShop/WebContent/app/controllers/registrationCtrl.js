webShop.controller('registrationController', function($scope, $rootScope, $location, userFactory) {
	
	var vm = this;	
	
		
	vm.register = function() {
		vm.message = '';
		$rootScope.users.push(vm.user);
		console.log($rootScope.users);
		userFactory.addUser(vm.user)
			.then(function(response) {
				vm.user = {};
				vm.message = response.data.message;
				$location.path('/');
			})
	

	}
	
});