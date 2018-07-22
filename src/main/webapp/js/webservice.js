(function() {
    'use strict';
    var app = angular.module('hs.WebService', []);
    app.factory('WebServiceX', function() {
        var ws = function(method, url, data, headers) {
            return $.ajax({
                type: method,
                url: url,
                headers: headers,
                contentType: 'application/json',
                dataType: 'json',
                data: data
            });
        }
        return {
            read: function(url, headers) {
                return ws('GET', url, {}, headers);
            },
            readAll: function(url, data, headers) {
                return ws('GET', url, data, headers);
            },
            create: function(url, data, headers) {
                return ws('POST', url, data, headers);
            },
            update: function(url, data, headers) {
                return ws('PUT', url, data, headers);
            },
            deleta: function(url, headers) {
                return ws('DELETE', url, {}, headers);
            }
        }
    });
})();