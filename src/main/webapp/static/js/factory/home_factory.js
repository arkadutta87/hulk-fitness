'use strict';

angular.module('homeApp').factory('HomeFactory', function($http) {
	var LOGOUT_SERVICE_URI = '../logout/';
	var INITIALIZE_URI = '../home/initialize/';
	var PACKAGES_URI = '../home/package/getpackages/';
	var DEL_PACKAGE_URI = '../home/package/delete/';
	var GET_PACKAGE_URI = '../home/package/read/';
	var EDIT_PACKAGE_URI = '../home/package/edit/';
	var ADD_PACKAGE_URI = '../home/package/add/';
	var CHANGE_PASSWORD_URI = '../home/changePassword/';
	return {

		logout : function(a) {

			return $http.post(LOGOUT_SERVICE_URI, a);
		},

		initializeURI : function() {
			return $http.get(INITIALIZE_URI);
		},

		getpackages : function(a) {

            return $http.post(PACKAGES_URI, a);
        },
        deletepackage : function(a) {

            return $http.post(DEL_PACKAGE_URI, a);
        },
        readPackage : function(a){
            return $http.post(GET_PACKAGE_URI, a);
        },
        editPackage : function(a){
            return $http.post(EDIT_PACKAGE_URI, a);
        },
        addPackage : function(a){
            return $http.post(ADD_PACKAGE_URI, a);
        },
        changePassword : function(a){
            return $http.post(CHANGE_PASSWORD_URI, a);
        }

	}
});