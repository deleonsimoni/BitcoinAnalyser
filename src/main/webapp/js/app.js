
angular.module("webApp", ["ui.router", "ui.mask", "starter.controllers", "starter.services", "starter.filters", "starter.directives", "hs.WebService"])

    .config(function ($stateProvider, $urlRouterProvider) {

        $stateProviderRef = $stateProvider;

          $stateProvider.state(STATE_INIT,{
                url: '/',
                abstract: false,
                views: {
                    'header-container': {
                        templateUrl: 'pages/header.html'
                    },
                    'menu-container': {
                        templateUrl: 'pages/menu.html'
                    },
                    'page-container': {
                        templateUrl: 'pages/main.html'
                    },
                    'footer-container': {
                        templateUrl: 'pages/footer.html'
                    }
                }
            });	
            
			var state = {
                url: '/splash',
                views: {
                    'page-container': {
                        templateUrl: 'pages/splash/Splash.html'
                    }
                }                
            };
					  
					  			
            $stateProvider.state('splash',state);
            
			var state = {
                url: '/principal',
                ncyBreadcrumb: {
	            	label: 'Principal'
						,parent:'splash'
                
                },
                
                views: {
                   /* 'header-container': {
                        templateUrl: 'pages/header.html'
                    },*/
                    'page-container': {
                        templateUrl: 'pages/principal/Principal.html'
                    },
                    'menu-container': {
                        templateUrl: 'pages/menu.html'
                    },
                    'footer-container': {
                        templateUrl: 'pages/footer.html'
                    }
                }                
            };
					  			
            $stateProvider.state('principal',state);

        $urlRouterProvider.otherwise('/');
    });

