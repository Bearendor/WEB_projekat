webShop.controller('podforumiController', function($scope, $rootScope, $location, podforumFactory) {
	$scope.isHidden = true;
	$scope.error = "";
	$scope.podforumi = [];
	$scope.podforum = {};
	$scope.podforum.spisakModeratora = [];
	$scope.podforum.ikonica = "";
	$scope.podforum.spisakPravila = [];
	$scope.moreItems = false;
	//
		
	function init() {
		podforumFactory.getPodforumi().success(function (data) {
        	$scope.podforumi = data;
        	//rasporediPodforume();
        	console.log($scope.podforumi)
		});
    }
	
	init();
	
	$scope.showHide = function() {
		//$scope.podforum.spisakModeratora = [];
		if($scope.isHidden == true)
			$scope.isHidden = false;
		else
			$scope.isHidden = true;
	}
	
	$scope.dodajPodforum = function() {
		$scope.message = '';
		console.log($scope.podforum);
		$scope.podforumi.push($scope.podforum);
		$scope.podforum.odgovorniModerator = $rootScope.log.username;
		podforumFactory.dodajPodforum($scope.podforum)
			.then(function(response) {
				//$scope.podforum.odgovorniModerator = $rootScope.log.username;
				rasporediPodforume();
				$scope.podforum = {};
				$scope.podforum.spisakModeratora = [];
				$scope.podforum.ikonica = "";
				$scope.podforum.spisakPravila = [];
				$scope.message = response.data.message;
			})
	}	
	

	$scope.toggleSelection = function(username) {
		var idx = $scope.podforum.spisakModeratora.indexOf(username);
		
		if(idx > -1) {
			$scope.podforum.spisakModeratora.splice(idx, 1);
		}
		else {
			$scope.podforum.spisakModeratora.push(username);
		}
	}
	
	$scope.obrisiPodforum = function(naziv) {
		podforumFactory.obrisiPodforum(naziv)
			.then(function(response) {
				init();
			});
	};
	
	function rasporediPodforume() {
		var count = $scope.podforumi.length;
		if(count > 5) {
			$scope.moreItems = true; 
		}
	}

});
