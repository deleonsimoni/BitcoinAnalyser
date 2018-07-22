angular.module("starter.controllers", ["ngAnimate", "smart-table", "ui.bootstrap"])
    .run(function ($rootScope, $state, $location, $filter, $timeout, Utils, Error, Log, WebResource, Alert, NativeInterface) {
        Log.info("controller.run()");

        try {
            $rootScope.home = 'splash';
            $rootScope.splash = 'splash';
            $rootScope.contextEditable = false;

            $rootScope.currentview = {};

            $rootScope.project = PROJECT;
            $rootScope.theme = $rootScope.project.theme;
            $rootScope.navlock = false;
            $rootScope.contextEditable = false;
            $rootScope.platforms = Utils.getPlatforms();

            $rootScope.goHome = function() {
                $rootScope.go($rootScope.home);
            }
            
            $rootScope.go = function(state) {
                $location.path("/" + state);
            }
            
            $rootScope.openViewDelayed = function(pagina,delay) {
                Log.debug("openViewDelayed(" + delay + ")");
                var delayms = delay * 1000;
                $timeout(function() {
                	$rootScope.go(pagina);
                },delayms);
            }

            $rootScope.go('splash');

        } catch (error) {
            Error.handler("Falha ao processar inicialização", error);
        }
    })



     .controller('AppController', function ($scope, $rootScope, Log) {
        Log.info("AppController()");
    })

    .controller('MenuController', function ($scope, $rootScope, Log) {
        Log.info("MenuController()");
    })

    .controller('HeaderController', function ($scope, $rootScope, $state, Log) {
        Log.info("HeaderController()");
        
       
    });