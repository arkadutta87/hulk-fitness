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
	var MEMBERS_URI = '../home/member/getmembers/';
	var ADD_MEMBER_URI = '../home/member/add/';
	var READ_MEMBER_URI = '../home/member/read/';
	var EDIT_MEMBER_URI = '../home/member/edit/';
	var PKG_MEMBER_LIST = '../home/customerpackageentity/list/';
	var PKG_MEMBER_DELETE = '../home/customerpackageentity/delete/';
	var PKG_MEMBER_ADD = '../home/customerpackageentity/add/';
	var PKG_MEMBER_READ = '../home/customerpackageentity/read/';
	var PROGRESS_ADD = '../home/memberpackageprogressentity/add/';
	var MEMBERS_EXP_URL = '../home/membersubscriptionexpired/list/';
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
        },

        getmembers : function(a) {
            return $http.post(MEMBERS_URI, a);
        },
        addmember : function(a) {
            return $http.post(ADD_MEMBER_URI, a);
        },
        readmember : function(a) {
            return $http.post(READ_MEMBER_URI, a);
        },
        editmember : function(a) {
            return $http.post(EDIT_MEMBER_URI, a);
        },

        getpkgmemberlist : function(a) {
            return $http.post(PKG_MEMBER_LIST, a);
        },
        deletepkgmember : function(a) {
            return $http.post(PKG_MEMBER_DELETE, a);
        },
        addpkgmember : function(a) {
            return $http.post(PKG_MEMBER_ADD, a);
        },
        readpkgmember : function(a) {
            return $http.post(PKG_MEMBER_READ, a);
        },

        progressadd : function(a) {
            return $http.post(PROGRESS_ADD, a);
        },

        getmembersexp : function(a) {
            return $http.post(MEMBERS_EXP_URL, a);
        }
	}
});