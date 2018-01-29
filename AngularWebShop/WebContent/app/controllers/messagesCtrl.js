webShop.controller('messagesController', function($scope, $rootScope, $location, $timeout, messagesFactory) {
	$scope.messages = messagesFactory;
	$scope.messages = [];
	$scope.message = messagesFactory;
	$scope.reply = messagesFactory;
	//$scope.message = {};
	
	$scope.unread = messagesFactory;
	$scope.read = messagesFactory;
	$scope.sent = messagesFactory;
	$scope.messageInfo = messagesFactory;
	
	
	function initMsgs() {
		messagesFactory.getMessages().success(function (data) {
        	$scope.messages = data;
        	$scope.unread = false;
        	$scope.read = false;
        	$scope.sent = false;
        	$scope.messageInfo = false;
        	$scope.message = {};
        	$scope.reply = {};
        	/*if(!$scope.message) {
        		$scope.messages.push($scope.message);
        	}*/
        	console.log($scope.messages)
		});
    };
	
	initMsgs();
	
	$scope.openModal = function(posiljalac, primalac) {
		$scope.message.posiljalac = primalac;
		$scope.message.primalac = posiljalac;
		$scope.message.naslov = "";
		$scope.message.sadrzaj = "";
		$scope.message.procitana = "N";
		
		//console.log($scope.reply);
	};
	$scope.clearMsg = function() {
		$scope.message.posiljalac = $rootScope.log.username;
		$scope.message.primalac = "";
		$scope.message.naslov = "";
		$scope.message.sadrzaj = "";
		$scope.message.procitana = "N";
	}
	
	$scope.replyMsg = function() {
		console.log($scope.message);
		messagesFactory.addMessage($scope.message).then(function(response) {
			$timeout(function() {
		        angular.element('#fireInit').triggerHandler('click');
		        $timeout(function() {
		        	angular.element('#replyMessage').modal('hide');
			    }, 0);
		    }, 0);
		})
	};
	
	
	
	$scope.showUnreadMessages = function() {
		$scope.messageInfo = false;
		
		$scope.unread = true;
    	$scope.read = false;
    	$scope.sent = false;
    	console.log($scope.unread)
	};
	
	$scope.showReadMessages = function() {
		$scope.messageInfo = false;
		
		$scope.unread = false;
    	$scope.read = true;
    	$scope.sent = false;
    	console.log($scope.read)
	};
	
	$scope.showSentMessages = function() {
		$scope.messageInfo = false; 
		
		$scope.unread = false;
    	$scope.read = false;
    	$scope.sent = true;
    	console.log($scope.sent)
	};
	
	$scope.showMessage = function(msg) {
		$scope.messageInfo = true;
		
		$scope.message.posiljalac = msg.posiljalac;
		$scope.message.primalac = msg.primalac;
		$scope.message.naslov = msg.naslov;
		$scope.message.sadrzaj = msg.sadrzaj;
		$scope.message.procitana = msg.procitana;
		$scope.message.id = msg.id;
		
		console.log($scope.message)
	};
	
	
	$scope.closeMsgInfo = function() {
		messagesFactory.changeMessage($scope.message).success(function() {		
			initMsgs();
		});
		$scope.messageInfo = false;
	};
	
	$scope.doInit = function() {
		initMsgs();
		console.log("Init fired");
		
	};
	
});

