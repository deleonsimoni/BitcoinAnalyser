'use strict';
var DEFAULT_VIEW = "principal";



angular.module('webApp').controller('SplashController', function ($scope, $timeout, $rootScope, Log, WebServiceX, Error, Utils) {
        Log.debug("SplashController()");
        $scope.currentview.description = 'Tela de Splash'; 
        
        $scope.bitcoinAnalyser = function() {
        	WebServiceX.read("aurum/bitcoin")
        	.then(function(res) {
        			console.info('Dados Carregados!' + res);
        			$rootScope.bitcoinAnalyser = res;
        			$rootScope.go('principal');
        			$scope.$apply();
        	}, function(xhr, status, err) {
      				var message = "Falha ao buscar dados do Bitcoin Analyser";
      				if (xhr && xhr.responseText) {
        				try {
        					var response = JSON.parse(xhr.responseText);
	      					if (response && response.msgsErro && response.msgsErro.length > 0) {
	      						message = response.msgsErro[0];
	      					}	        					        					
        				} catch(ignore) {
        				}
      				}
      				Error.handler(message, err);
        	});
        };   
        
        $timeout(function() {
            $scope.bitcoinAnalyser();
        },4000);
        
    });

angular.module('webApp').controller('PrincipalController', function ($scope, $rootScope, Log, WebServiceX, Error, Utils) {
        Log.debug("PrincipalController()");
        $scope.currentview.description = 'Tela Principal';   
        
        
        
    });

