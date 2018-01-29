webShop.controller('loginController', function($scope, $rootScope, $location, $timeout, userFactory) {
	$scope.users = userFactory;
	$rootScope.log = "";
	$rootScope.route = "";
	$rootScope.users = [];
	$scope.loggedInUser = "";
	$rootScope.enabled = "";
	$scope.userInfo = userFactory;
	$scope.userMessages = userFactory;
	
	function init() {
		userFactory.getUsers().success(function (data) {
			//delete $scope.users;
			//$scope.loggedInUser = "";
			$rootScope.log = "";
			$rootScope.users = data;
        	$scope.users = data;
        	$rootScope.enabled = "";
        	$scope.userInfo = false;
        	$scope.userMessages = false;
		});
    }
	
	init();
    
    $scope.submit = function() {
		$scope.error = " ";
		$scope.users = ($rootScope.users).slice();
		console.log($scope.users);
		console.log($rootScope.users);
		var user = $scope.username;
		var pass = $scope.password;
		//

		if($scope.users.find(item => item.username === user) && $scope.users.find(item => item.password === pass))
		{		
			$timeout(function() {
		        angular.element('#forumatorHome').triggerHandler('click');
		    }, 0);
			$scope.loggedInUser = $scope.users.find(item => item.username === user) && $scope.users.find(item => item.password === pass);
			$rootScope.log = $scope.users.find(item => item.username === user) && $scope.users.find(item => item.password === pass);
			putanja();
			if($scope.loggedInUser.role == "K") {
				console.log($scope.loggedInUser);
				//$location.path('/profilePageUser');
				$location.path('/mainPage');
			}
			else if($scope.loggedInUser.role == "M") {
				console.log($scope.loggedInUser);
				//$location.path('/profilePageModerator');
				$location.path('/mainPage');
			}
			else if($scope.loggedInUser.role == "A") {
				console.log($scope.loggedInUser);
				//$location.path('/profilePageAdmin');
				$location.path('/mainPage');
			}
		}
		else
			$scope.error = "Wrong username or password"		
	}	
    
    
    $scope.promeniUlogu = function() {
    	console.log($rootScope.users)
    	console.log($scope.users)
    }
    
    $scope.odjavljivanje = function() {
    	$console.loggedInUser = "";
    	$rootScope.log = "";
    }
    
    $scope.doDisable = function() {
    	$rootScope.enabled = "N";
    	console.log($rootScope.enabled);
    }
    $scope.doEnable = function() {
    	$rootScope.enabled = "Y";
    	console.log($rootScope.enabled);
    }
    
    $scope.showInfo = function() {
    	$scope.userInfo = true;
    	$scope.userMessages = false;
    	console.log($scope.userInfo);
    }
    
    $scope.showMessages = function() {
    	$scope.userInfo = false;
    	$scope.userMessages = true;
    	console.log($scope.userMessages)
    }
    
    function putanja() {
    	if($rootScope.log.role == "K") {
    		$rootScope.route = "profilePageUser";
    	} else if ($rootScope.log.role == "M") {
    		$rootScope.route = "profilePageModerator";
    	} else if ($rootScope.log.role == "A") {
    		$rootScope.route = "profilePageAdmin";
    	} 
    }
    
});






