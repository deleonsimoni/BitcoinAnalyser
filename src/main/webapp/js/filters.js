angular.module('starter.filters', [])
	.filter('capitalize', function() {
	  return function(input, scope) {
	    if (input!=null)
	    input = input.toLowerCase();
	    return input.substring(0,1).toUpperCase()+input.substring(1);
	  }
	})
	.filter('split', function() {
		return function(input, splitChar, splitIndex) {
			var value = input.split(splitChar);
			if (value.length >= (splitIndex + 1)) {
				return value[splitIndex];
			}
		}
	});
  	

	
	
