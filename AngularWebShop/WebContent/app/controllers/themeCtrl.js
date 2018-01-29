webShop.controller('themeController', function($scope, $rootScope, $location, $timeout, themeFactory, podforumFactory) {
	$scope.themes = [];
	$scope.podforumi = [];
	$scope.theme = themeFactory;
	$rootScope.naslov = "";
	
		
	function init() {
		themeFactory.getThemes().success(function (data) {
        	$scope.themes = data;
        	console.log($scope.themes)
		});
		podforumFactory.getPodforumi().success(function (data) {
        	$scope.podforumi = data;
        	console.log($scope.podforumi);
		});
    }
	
	init();
	
	$scope.sendTitle = function(title) {
		init();
		$rootScope.naslov = title;
		var pom = $scope.podforumi.find(x => x.naziv == title);
		$rootScope.izabraniPodforum = pom;
		console.log($rootScope.izabraniPodforum);
		//console.log($rootScope.naslov)
	}
	
	$scope.doInit = function() {
		init();
	}
	
	$scope.sendTheme = function(theme) {
		//$scope.theme = theme;
		//console.log($scope.theme)
		$scope.theme.podforum = theme.podforum;
		$scope.theme.naslov = theme.naslov;
		$scope.theme.tip = theme.tip;
		$scope.theme.sadrzaj = theme.sadrzaj;
		$scope.theme.autor = theme.autor;
		$scope.theme.datumKreiranja = theme.datumKreiranja;
		$scope.theme.pozitivniGlasovi = theme.pozitivniGlasovi;
		$scope.theme.negativniGlasovi = theme.negativniGlasovi;
		console.log($scope.theme)
	}
	
	$scope.pozUp = function() {
		voteUp();
		themeFactory.promeniGlas($scope.theme);
		init();
	}
	
	$scope.negUp = function() {
		voteDown();
		themeFactory.promeniGlas($scope.theme);
		init();
	}
	
	$scope.prepareTheme = function() {
		$scope.theme.podforum = $rootScope.izabraniPodforum.naziv;
		$scope.theme.naslov = "";
		$scope.theme.tip = "";
		$scope.theme.sadrzaj = "";
		$scope.theme.autor = $rootScope.log.username;
		$scope.theme.datumKreiranja = "";
		$scope.theme.pozitivniGlasovi = [];
		$scope.theme.negativniGlasovi = [];
	}
	
	$scope.createTheme = function() {
		angular.element('#newTheme').modal('hide');
		console.log($scope.theme);
		
		themeFactory.createTheme($scope.theme).success(function () {
			$timeout(function() {
		        angular.element('#initThemes').triggerHandler('click');
		    }, 0);
		});	
	}
	
	
	function voteUp() {
		if($scope.theme.pozitivniGlasovi.indexOf($rootScope.log.username) === -1) {
			if($scope.theme.negativniGlasovi.indexOf($rootScope.log.username) === -1) {
				$scope.theme.pozitivniGlasovi.push($rootScope.log.username);
			} else {
				var index = $scope.theme.negativniGlasovi.indexOf($rootScope.log.username);
				$scope.theme.negativniGlasovi.splice(index, 1);
				$scope.theme.pozitivniGlasovi.push($rootScope.log.username);
			}
		}
		
	}
	
	function voteDown() {
		if($scope.theme.negativniGlasovi.indexOf($rootScope.log.username) === -1) {
			if($scope.theme.pozitivniGlasovi.indexOf($rootScope.log.username) === -1) {
				$scope.theme.negativniGlasovi.push($rootScope.log.username);
			} else {
				var index = $scope.theme.pozitivniGlasovi.indexOf($rootScope.log.username);
				$scope.theme.pozitivniGlasovi.splice(index, 1);
				$scope.theme.negativniGlasovi.push($rootScope.log.username);
			}
		}
	}
	
	


});