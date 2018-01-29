webShop.controller('commentController', function($scope, $rootScope, $location, $timeout, commentFactory) {
	$scope.comments = commentFactory;
	$scope.comments = [];
	$scope.showIt = false;
	$scope.showReply = false;
	$scope.childComment = commentFactory;
	$scope.comment = commentFactory;
	$scope.parComm = commentFactory;
	$scope.comment.pozitivniGlasovi = commentFactory;
	$scope.text = commentFactory;
	// $scope.commID = "";
	// $scope.comment = {};
	// $scope.comment.tekstKomentara = "";
	// $scope.comment.pripadaTemi = "";
	
	
	function init() {
		commentFactory.getComments().success(function (data) {
        	$scope.comments = data;
        	console.log($scope.comments)
		});
    };
	
	init();
	
	$scope.showHideNewComment = function() {
		$scope.comment = {};
		$scope.comment.tekstKomentara = "";
		$scope.comment.pripadaTemi = "";
		if($scope.showIt) {
			$scope.showIt = false;
		} else {
			$scope.showIt = true;
		}
	};
	
	$scope.$on("myEvent", function (event, args) {
		   console.log(args.chosen)
		});
	
	$scope.transferParent = function(comm) {
		console.log("Clean Slate!");
		$scope.childComment.tekstKomentara = "";
		$scope.childComment.id = "";
		$scope.childComment.autor = "";
		$scope.childComment.datumKomentara = "";
		$scope.childComment.pozitivniGlasovi = [];
		$scope.childComment.negativniGlasovi = [];
		$scope.childComment.menjan = "";
		$scope.childComment.pripadaTemi = "";
		$scope.childComment.roditeljskiKomentar = "";
		$scope.childComment.podkomentari = [];
		$scope.parComm.tekstKomentara = comm.tekstKomentara;
		$scope.parComm.id = comm.id;
		$scope.parComm.autor = comm.autor;
		$scope.parComm.datumKomentara = comm.datumKomentara;
		$scope.parComm.pozitivniGlasovi = comm.pozitivniGlasovi;
		$scope.parComm.negativniGlasovi = comm.negativniGlasovi;
		$scope.parComm.menjan = comm.menjan;
		$scope.parComm.pripadaTemi = comm.pripadaTemi;
		$scope.parComm.roditeljskiKomentar = comm.roditeljskiKomentar;
		$scope.parComm.podkomentari = comm.podkomentari;
		console.log($scope.parComm);

	};

	$scope.addComment = function(tema) {
		console.log(tema);
		$scope.message = '';
		$scope.comment.pripadaTemi = tema;
		$scope.comment.autor = $rootScope.log.username;
		commentFactory.addComment($scope.comment)
			.then(function(response) {
				$scope.comment = {};
				$scope.showIt = false;
				init();
			})
	};
	
	$scope.reply = function() {
		// $scope.comment = comment;
		$scope.childComment.autor = $rootScope.log.username;
		var data = {'parent': $scope.parComm, 'child': $scope.childComment}
		
		commentFactory.reply(data).success(function() {
			$scope.childComment.tekstKomentara = "";
			$scope.childComment.id = "";
			$scope.childComment.autor = "";
			$scope.childComment.datumKomentara = "";
			$scope.childComment.pozitivniGlasovi = [];
			$scope.childComment.negativniGlasovi = [];
			$scope.childComment.menjan = "";
			$scope.childComment.pripadaTemi = "";
			$scope.childComment.roditeljskiKomentar = "";
			$scope.childComment.podkomentari = [];
			
			$scope.parComm.tekstKomentara = "";
			$scope.parComm.id = "";
			$scope.parComm.autor = "";
			$scope.parComm.datumKomentara = "";
			$scope.parComm.pozitivniGlasovi = [];
			$scope.parComm.negativniGlasovi = [];
			$scope.parComm.menjan = "";
			$scope.parComm.pripadaTemi = "";
			$scope.parComm.roditeljskiKomentar = "";
			$scope.parComm.podkomentari = [];
			$timeout(function() {
		        angular.element('#MyButtonTest').triggerHandler('click');
		    }, 0);
		});
	};
	
	$scope.setCommentId = function(id) {
		// $scope.commID = id;
		var comm = $scope.comments.find(item => item.id === id);
		$scope.comment.tekstKomentara = comm.tekstKomentara;
		$scope.comment.id = comm.id;
		$scope.comment.autor = comm.autor;
		$scope.comment.datumKomentara = comm.datumKomentara;
		$scope.comment.pozitivniGlasovi = comm.pozitivniGlasovi;
		$scope.comment.negativniGlasovi = comm.negativniGlasovi;
		$scope.comment.menjan = comm.menjan;
		$scope.comment.pripadaTemi = comm.pripadaTemi;
		$scope.comment.roditeljskiKomentar = comm.roditeljskiKomentar;
		$scope.comment.podkomentari = comm.podkomentari;
		console.log($scope.comment);
	};
	
	$scope.edit = function() {
		angular.element('#myModal').modal('hide');
		commentFactory.edit($scope.comment).success(function() {
			$scope.comment.tekstKomentara = "";
			$scope.comment.id = "";
			$scope.comment.autor = "";
			$scope.comment.datumKomentara = "";
			$scope.comment.pozitivniGlasovi = [];
			$scope.comment.negativniGlasovi = [];
			$scope.comment.menjan = "";
			$scope.comment.pripadaTemi = "";
			$scope.comment.roditeljskiKomentar = "";
			$scope.comment.podkomentari = [];
			$timeout(function() {
		        angular.element('#MyButtonTest').triggerHandler('click');
		    }, 0);
			init();
    		
    	});
	}
	
	$scope.deleteComment = function(comment) {
		commentFactory.deleteComment(comment).success(function() {
			init();
		});
	}
	
	$scope.doInit = function() {
		console.log("INITOVANJE!");
		init();
	}
	
	$scope.pozUp = function(com) {
		if($rootScope.log){
			console.log("Presao");
			voteUp(com);
		} else {
			$scope.comment = com;
			commentFactory.promeniGlas($scope.comment).success(function() {
	    		init();
	    	});
		}
	}
	
	$scope.negUp = function(com) {
		if($rootScope.log){
			console.log("Presao");
			voteDown(com);
		} else {
			$scope.comment = com;
			commentFactory.promeniGlas($scope.comment).success(function() {
	    		init();
	    	});
		}
	}
	
	$scope.readyForComplaint = function(comm) {
		$rootScope.izabraniKomentar = comm;
	}
	
	
	
	function voteUp(com) {
		$scope.comment = com;
		if($scope.comment.pozitivniGlasovi.indexOf($rootScope.log.username) === -1) {
			if($scope.comment.negativniGlasovi.indexOf($rootScope.log.username) === -1) {
				$scope.comment.pozitivniGlasovi.push($rootScope.log.username);
			} else {
				var index = $scope.comment.negativniGlasovi.indexOf($rootScope.log.username);
				$scope.comment.negativniGlasovi.splice(index, 1);
				$scope.comment.pozitivniGlasovi.push($rootScope.log.username);
			}
		}
		
	}
	
	function voteDown(com) {
		$scope.comment = com;
		if($scope.comment.negativniGlasovi.indexOf($rootScope.log.username) === -1) {
			if($scope.comment.pozitivniGlasovi.indexOf($rootScope.log.username) === -1) {
				$scope.comment.negativniGlasovi.push($rootScope.log.username);
			} else {
				var index = $scope.comment.pozitivniGlasovi.indexOf($rootScope.log.username);
				$scope.comment.pozitivniGlasovi.splice(index, 1);
				$scope.comment.negativniGlasovi.push($rootScope.log.username);
			}
		}
		
	}
	
		
});

