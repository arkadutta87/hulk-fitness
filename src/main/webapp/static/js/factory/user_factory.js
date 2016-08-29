'use strict';

angular.module('myApp').factory('UserFactory', function($http) {
    var REST_SERVICE_URI = 'user/';
    return {
        fetchAllUsers: function() {
            return $http.get(REST_SERVICE_URI);
        },

        createUser: function(user) {
            return $http.post(REST_SERVICE_URI,user);
        },

        updateUser : function(user, id) {
            //var deferred = $q.defer();
            return $http.put(REST_SERVICE_URI+id, user);
        },

        deleteUser : function(id) {
            return $http.delete(REST_SERVICE_URI+id);

        },

        users: function() {
            return $http.get('/vms/users/');
        },

        login: function(a) {

            return $http.post('/vms/login/', a);
        },

    }
});