// napravimo modul

var webShop = angular.module('webShop', ['ngRoute', 'ui.bootstrap']);

    
webShop.config(function($routeProvider) {
	$routeProvider.when('/',
	{
		templateUrl: 'partials/login.html'
	}
	).when('/registration',
	{
		templateUrl: 'partials/registration.html'
	}
	).when('/profilePageUser',
	{
		templateUrl: 'partials/profilePageUser.html'
	}
	).when('/profilePageModerator',
	{
		templateUrl: 'partials/profilePageModerator.html'
	}
	).when('/profilePageAdmin',
	{
		templateUrl: 'partials/profilePageAdmin.html'
	}
	).when('/sviKorisnici',
	{
		templateUrl: 'partials/sviKorisnici.html'
	}
	).when('/sviPodforumi',
	{
		templateUrl: 'partials/sviPodforumi.html'
	}
	).when('/zalbe',
	{
		templateUrl: 'partials/zalbe.html'
	}
	).when('/poruke',
	{
		templateUrl: 'partials/poruke.html'
	}
	).when('/mainPage',
	{
		templateUrl: 'partials/mainPage.html'
	}
	).when('/podforum',
	{
		templateUrl: 'partials/podforum.html'
	}
	).when('/tema',
	{
		templateUrl: 'partials/tema.html'
	}
	)
});

webShop.config(function($logProvider){
    $logProvider.debugEnabled(true);
});

webShop.run(function($rootScope) {
	$rootScope.enabled = "Y";
    console.log($rootScope.enabled);
});