webShop.controller('complaintsController', function($scope, $rootScope, $location, complaintsFactory, podforumFactory, messagesFactory, themeFactory, commentFactory) {
	$scope.complaints = complaintsFactory;
	$scope.complaint = complaintsFactory;
	$scope.complaint = {};
	
	$scope.themes = [];
	$scope.comments = [];
	$scope.subforums = [];
	
	$scope.actions = ["Delete", "Warn Author", "Reject"];
	$scope.chosenAction = null;
	$scope.c = null;
	$scope.messageDeny = messagesFactory;
	$scope.messageWarning1 = messagesFactory;
	$scope.messageWarning2 = messagesFactory;	
    
	function init() {
		complaintsFactory.getComplaints().success(function (data) {
        	$scope.complaints = data;
        	$scope.messageDeny.naslov = "Complaint review";
        	$scope.messageDeny.sadrzaj = "We reviewed your complaint, but must inform you that we see no reason to take further actions.";
        	$scope.messageDeny.posiljalac = $rootScope.log.username;
        	$scope.messageDeny.procitana = "N";	
        	$scope.messageWarning1 = 
    		{
    			naslov : "Complaint review",
    			sadrzaj : "We reviewed your complaint, and after careful consideration decided to warn the author of entity.",
    			posiljalac : $rootScope.log.username,
    			procitana : "N"
    		};
        	$scope.messageWarning2 = 
    		{
    			naslov : "Complaint about",
    			sadrzaj : "",
    			posiljalac : $rootScope.log.username,
    			procitana : "N"
    		};
        	
        	$scope.complaint.tekstZalbe = "";
        	
        	
        	themeFactory.getThemes().success(function (data) {
            	$scope.themes = data;
    		});
        	commentFactory.getComments().success(function (data) {
            	$scope.comments = data;
    		});
        	podforumFactory.getPodforumi().success(function (data) {
            	$scope.subforums = data;
    		});
        	
		});
		
    }
    
	init();
	
	$scope.initAction = function(zalba){
		console.log($scope.c);
		
		if($scope.c == "Reject") {
			console.log("Reject");
			$scope.messageDeny.primalac = zalba.podnosilacZalbe;
			messagesFactory.addMessage($scope.messageDeny);
		} else if($scope.c == "Warn Author") {
			console.log("Warning");
			$scope.messageWarning1.primalac = zalba.podnosilacZalbe;
			
			if(zalba.entitetZalbe == "comment") {
				$scope.messageWarning2.naslov += " comment";
				var temaKomentara = $scope.comments.find(x => x.id == zalba.idEntiteta).pripadaTemi;
				$scope.messageWarning2.sadrzaj = "There was a complaint about a comment you posted on theme '" + temaKomentara + 
												"' . Please be more carefull next time!";
				var primalacZalbe = $scope.comments.find(x => x.id == zalba.idEntiteta).autor;
				$scope.messageWarning2.primalac = primalacZalbe;
				
				messagesFactory.addMessage($scope.messageWarning1);
				messagesFactory.addMessage($scope.messageWarning2);
				
			} else if(zalba.entitetZalbe == "theme") {
				$scope.messageWarning2.naslov += " theme";
				$scope.messageWarning2.sadrzaj = "There was a complaint about a theme '" 
												+ zalba.idEntiteta + "' you posted in subforum '"+zalba.zalPodforum 
												+ "' . Please be more carefull next time!";
				var primalacZalbe = $scope.themes.find(x => x.podforum == zalba.zalPodforum && x.naslov == zalba.idEntiteta).autor;
				$scope.messageWarning2.primalac = primalacZalbe;
				
				messagesFactory.addMessage($scope.messageWarning1);
				messagesFactory.addMessage($scope.messageWarning2);
				
			} else if(zalba.entitetZalbe == "subforum") {
				$scope.messageWarning2.naslov += " subforum";
				$scope.messageWarning2.sadrzaj = "There was a complaint about a subforum '" 
												+ zalba.zalPodforum + "' you posted. Keep an eye out, will you?!";
				var primalacZalbe = $scope.subforums.find(x => x.naziv == zalba.zalPodforum).odgovorniModerator;
				$scope.messageWarning2.primalac = primalacZalbe;
				
				messagesFactory.addMessage($scope.messageWarning1);
				messagesFactory.addMessage($scope.messageWarning2);
			}
			
			console.log($scope.messageWarning2);
			
			
		} else if($scope.c == "Delete") {
			console.log("Delete");
			$scope.messageWarning1.primalac = zalba.podnosilacZalbe;
			
			if(zalba.entitetZalbe == "comment") {
				$scope.messageWarning1.sadrzaj = "We reviewed your complaint and decided to delete that entity which we find offensive!"
				$scope.messageWarning2.naslov += " comment";
				var kom = $scope.comments.find(x => x.id == zalba.idEntiteta);
				$scope.messageWarning2.sadrzaj = "There was a complaint about a comment you posted at theme '" 
												+ kom.pripadaTemi + "'. It had to be deleted because it violated the rules.";
				var primalacZalbe = kom.autor;
				$scope.messageWarning2.primalac = primalacZalbe;
				messagesFactory.addMessage($scope.messageWarning1);
				messagesFactory.addMessage($scope.messageWarning2);
				commentFactory.deleteComment(kom);
			} else if(zalba.entitetZalbe == "theme") {
				$scope.messageWarning1.sadrzaj = "We reviewed your complaint and decided to delete that entity which we find offensive!"
				$scope.messageWarning2.naslov += " theme";
				var tema = $scope.themes.find(x => x.naslov == zalba.idEntiteta && x.podforum == zalba.zalPodforum);
				$scope.messageWarning2.sadrzaj = "There was a complaint about a theme '"+ tema.naslov+ "' at subforum '" + tema.podforum + 
												"'. It had to be deleted because it violated the rules.";
				var primalacZalbe = tema.autor;
				$scope.messageWarning2.primalac = primalacZalbe;
				messagesFactory.addMessage($scope.messageWarning1);
				messagesFactory.addMessage($scope.messageWarning2);
				themeFactory.deleteTheme(tema);
			} else if(zalba.entitetZalbe == "subforum") {
				$scope.messageWarning1.sadrzaj = "We reviewed your complaint and decided to delete that entity which we find offensive!"
				$scope.messageWarning2.naslov += " subforum";
				$scope.messageWarning2.sadrzaj = "There was a complaint about a subforum '" 
												+ zalba.zalPodforum + "' you posted. It had to be deleted because it violated the rules.";
				var primalacZalbe = $scope.subforums.find(x => x.naziv == zalba.zalPodforum).odgovorniModerator;
				$scope.messageWarning2.primalac = primalacZalbe;
				
				messagesFactory.addMessage($scope.messageWarning1);
				messagesFactory.addMessage($scope.messageWarning2);
				podforumFactory.obrisiPodforum(zalba.zalPodforum);
			}
		} else {
			console.log("None of the above.");
		}
		complaintsFactory.deleteComplaint(zalba).success(function () {
        	init();
		});
	};
	
	$scope.reportSubforum = function(podforum) {
		$scope.complaint.entitetZalbe = "subforum";
		$scope.complaint.zalPodforum = podforum.naziv;
		$scope.complaint.idEntiteta = "";
		$scope.complaint.podnosilacZalbe = $rootScope.log.username;
		
		complaintsFactory.addComplaint($scope.complaint)
		.then(function(response) {
			$scope.complaint = {};
			init();
		})
		
		console.log($scope.complaint);
		console.log("Reporting subforum!");
	}
	
	$scope.reportComment = function(komentar) {
		console.log(komentar);
		$scope.complaint.entitetZalbe = "comment";
		$scope.complaint.zalPodforum = "";
		$scope.complaint.idEntiteta = komentar.id;
		$scope.complaint.podnosilacZalbe = $rootScope.log.username;
		console.log(komentar.id);
		console.log($scope.complaint);
		complaintsFactory.addComplaint($scope.complaint)
		.then(function(response) {
			$scope.complaint = {};
			init();
		})
	}
	
	$scope.reportTheme = function(tema) {
		$scope.complaint.entitetZalbe = "theme";
		$scope.complaint.zalPodforum = tema.podforum;
		$scope.complaint.idEntiteta = tema.naslov;
		$scope.complaint.podnosilacZalbe = $rootScope.log.username;
		console.log($scope.complaint);
		complaintsFactory.addComplaint($scope.complaint)
		.then(function(response) {
			$scope.complaint = {};
			init();
		})
	}
	
	$scope.choseOne = function(one) {
		$scope.c = one;
	}
	
});