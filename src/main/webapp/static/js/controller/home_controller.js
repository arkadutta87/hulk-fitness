'use strict';

angular.module('homeApp').controller('HomeController', ['$scope', '$cookies', '$timeout', '$interval','HomeFactory',
    function($scope, $cookies, $timeout, $interval, HomeFactory) {

        $scope.currentPage = "../../static/html/firstPage.html";//"/epme/static/html/index.html";

        var self = this;
        self.logoutFn = logout;
        self.profileFn = profile;


        $scope.init = function(){

            setTimeout(function() {
                $('#myModal').modal('hide');
            },2500);
        }

        $scope.init();

        function profile(){
           $('#myModal').modal('show');
           $scope.currentPage = "../../static/html/profile.html";
           setTimeout(function() {
               $('#myModal').modal('hide');
           },1500);
        }

        function logout() {
            //start the modal
            $('#myModal').modal('show');
            var tokenStr = $cookies.get('epme_session');
            if(tokenStr == null ||  tokenStr == ''){
                //log this guy out
                $('#myModal').modal('hide');
                location.reload();
            }else{
                var logOutObj = {
                    token : tokenStr
                }

                HomeFactory.logout(logOutObj)
                    .success(function(data){
                        var code = data.code;
                        console.log("code sent -- "+code);
                        if(code == 1){
                            $('#myModal').modal('hide');
                            location.reload();
                        }else if(code == 0){
                            $cookies.remove('epme_session', {
                                path: '/'
                            });
                            $('#myModal').modal('hide');
                            window.location.href = data.redirectUrl;
                        }else{
                            $('#myModal').modal('hide');
                            alert('Log-out Failed. Please try again.');
                        }
                    })
                    .error(function(error) {
                        console.log('Error while login out - the error description - ' + error);
                        $('#myModal').modal('hide');
                        alert('Log-out Failed. Please try again.');
                        //$scope.status = true;
                        //$scope.showRes = "Internal Server Error. Contact Administrator";
                });
            }
        }

    }
]);