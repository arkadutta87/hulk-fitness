'use strict';

angular.module('loginApp').controller('LoginController', ['$scope', '$cookies', '$timeout', '$interval','LoginFactory',
    function($scope, $cookies, $timeout, $interval, LoginFactory) {

        $scope.init = function() {
            $scope.status = false;
            $scope.showRes = "";
            $scope.mobNo = "";
            $scope.otp = "";
            $scope.showCorrect = false;
            $scope.showWrong = false;
            $scope.pwdMatch = true;

            //check whether token in in cookie the start the animation
            var tokenStr = $cookies.get('pwd_token');
            if (tokenStr != null && tokenStr != '') {
                setTimeout(function() {
                    //location.reload();
                    $('#myModal').modal('show');
                    setTimeout(function() {
                        $('#myModal').modal('hide');

                        setTimeout(function() {
                            $('#loginPart').addClass('fadeOutLeft');
                            setTimeout(function() {
                                $('#register').css("opacity", "0.7");
                                $('#register').addClass('fadeInRight');
                            }, 500);
                        }, 500);
                    }, 500);
                }, 500);
            }
        }


        var self = this;

        self.login = {
            username: '',
            pwd: ''
        }

        self.pwd = {
            passwd: '',
            retypePwd: ''
        }

        self.users = [];

        self.changePwd = changePwd;
        //self.edit = edit;
        self.loginFn = login;
        self.reset = reset;


        $scope.init();

        $scope.checkStatus = function() {
            return $scope.status;
        }

        $scope.matchPwd = function() {
            if (self.pwd.passwd != '' && self.pwd.passwd == self.pwd.retypePwd) {
                $scope.showCorrect = true;
                $scope.showWrong = false;
                $scope.pwdMatch = false;
            } else {
                $scope.showWrong = true;
                $scope.pwdMatch = true;
                $scope.showCorrect = false;
            }
        }


        $scope.disableError = function() {
            if ($scope.status) {
                $scope.status = false;
                $scope.showRes = '';
            }
        }

        //fetchAllUsers();

        function reset() {
            self.login = {
                username: '',
                pwd: ''
            };
            $scope.myForm.$setPristine(); //reset Form
            $scope.status = false;
            $scope.showRes = '';
        }

        function changePwd() {
            //start the modal
            $('#myModal').modal('show');

            //get the token from the cookie
            var tokenStr = $cookies.get('pwd_token');
            if (tokenStr == null || tokenStr == '') {
                alert("Token Expired. Restart all over again.")
                setTimeout(function() {
                    location.reload();
                }, 500);
            } else {
                var changePwdObj = {
                    token: tokenStr,
                    newPassword: self.pwd.passwd
                }

                LoginFactory.changePwd(changePwdObj)
                    .success(function(data) {

                        /*private int code;
                            private String token ;
                            private String redirectUrl;
                            private String message;
                            */
                        var code = data.code;
                        if (code == 0) {
                            var token = data.token;
                            var dateExp = new Date();
                            dateExp.setTime(dateExp.getTime() + (365 * 24 * 60 * 60 * 1000));
                            $cookies.put('epme_session', token, {
                                expires: dateExp,
                                path: '/'
                            });

                            $cookies.remove('pwd_token', {
                                path: '/'
                            });

                            //do the animation
                            setTimeout(function() {
                                $('#myModal').modal('hide');

                                window.location.href = data.redirectUrl;
                            }, 200);
                        } else if (code == 1) {
                            //console.log('Error while changing password - the error description - '+error );
                            $('#myModal').modal('hide');
                            $scope.status = true;
                            $scope.showRes = data.message;

                            $cookies.remove('pwd_token', {
                                path: '/'
                            });
                            alert("Token Expired. Restart all over again.")
                            setTimeout(function() {
                                location.reload();
                            }, 500);

                        } else {
                            $('#myModal').modal('hide');
                            $scope.status = true;
                            $scope.showRes = data.message;

                        }
                    })
                    .error(function(error) {
                        console.log('Error while changing password - the error description - ' + error);
                        $('#myModal').modal('hide');
                        $scope.status = true;
                        $scope.showRes = "Internal Server Error. Contact Administrator or Try Again";
                    })

            }




        }

        function login() {
            //start the modal
            $('#myModal').modal('show');
            var loginObj = {
                username: self.login.username,
                password: self.login.pwd

            }

            LoginFactory.login(loginObj)
                .success(function(data) {
                    //save the token based on code in the cookie
                    var code = data.code;
                    console.log('test34' + data);

                    if (code == 1) {
                        //save token in cookie with name pwd_token, stop the modal , do the animation
                        var token = data.token;
                        var dateExp = new Date();
                        dateExp.setTime(dateExp.getTime() + (35 * 60 * 1000));
                        $cookies.put('pwd_token', token, {
                            expires: dateExp,
                            path: '/'
                        });

                        //do the animation
                        setTimeout(function() {
                            $scope.status = false;
                            $scope.showRes = '';
                            $('#myModal').modal('hide');

                            setTimeout(function() {
                                $('#loginPart').addClass('fadeOutLeft');
                                setTimeout(function() {
                                    $('#register').css("opacity", "0.7");
                                    $('#register').addClass('fadeInRight');
                                }, 300);
                            }, 500);
                        }, 200);


                    } else if (code == 0) {
                        //save token in cookie with name pwd_token
                        var token = data.token;
                        var dateExp = new Date();
                        dateExp.setTime(dateExp.getTime() + (365 * 24 * 60 * 60 * 1000));
                        $cookies.put('epme_session', token, {
                            expires: dateExp,
                            path: '/'
                        });

                        //do the animation
                        setTimeout(function() {
                            $('#myModal').modal('hide');

                            window.location.href = data.redirectUrl;
                        }, 200);

                    } else {
                        $('#myModal').modal('hide');
                        var reason = data.message;
                        $scope.status = true;
                        $scope.showRes = reason;
                        console.log("Reason -- " + reason);

                    }
                })
                .error(function(error) {
                    console.log('Error while login - the error description - ' + error);
                    $('#myModal').modal('hide');
                    $scope.status = true;
                    $scope.showRes = "Internal Server Error. Contact Administrator";
                });


        }
    }
]);