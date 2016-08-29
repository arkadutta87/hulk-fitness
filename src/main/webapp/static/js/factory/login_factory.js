'use strict';

angular.module('loginApp').factory('LoginFactory', function($http) {
    var LOGIN_SERVICE_URI = 'login/';
    var CHANGE_PWD_URI = 'changePassword/';
    var LOGOUT_SERVICE_URI = 'logout/';
    return {

        logout: function(a) {

            return $http.post(LOGIN_SERVICE_URI, a);
        },

        changePwd: function(a) {

            return $http.post(CHANGE_PWD_URI, a);
        },

        login: function(a) {

            return $http.post(LOGIN_SERVICE_URI, a);
        },

    }
});