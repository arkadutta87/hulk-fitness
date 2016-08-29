'use strict';

angular.module('homeApp').factory('HomeFactory', function($http) {
    var LOGOUT_SERVICE_URI = '../logout/';
    return {

        logout: function(a) {

            return $http.post(LOGOUT_SERVICE_URI, a);
        },

    }
});