<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Hulk Fitness! | </title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/static/images/hulk_logo.png' />" />

    <!-- Bootstrap -->
    <link href="<c:url value='/static/vendors/bootstrap/dist/css/bootstrap.css' />" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value='/static/vendors/font-awesome/css/font-awesome.min.css' />" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value='/static/vendors/nprogress/nprogress.css' />" rel="stylesheet">
    <!-- Animate.css -->
    <link href="<c:url value='/static/vendors/animate.css/animate.min.css' />" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="<c:url value='/static/css/custom.min.css' />" rel="stylesheet">

    <script type="text/javascript" src="<c:url value='/static/vendors/jquery/dist/jquery.min.js' />" ></script>
    <script type="text/javascript" src="<c:url value='/static/vendors/bootstrap/dist/js/bootstrap.min.js' />" ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-cookies.js"></script>
    <script src="<c:url value='/static/js/login.js' />"></script>
    <script src="<c:url value='/static/js/controller/login_controller.js' />"></script>
    <script src="<c:url value='/static/js/factory/login_factory.js' />"></script>

    <!--script>
    $(document).ready(function () {


    function openModal() {
      $('#myModal').modal('show');
    }

    function hideModal() {
      $('#myModal').modal('hide');

      setTimeout(function () {
        $('#loginPart').addClass('fadeOutLeft');
        setTimeout(function() {
          $('#register').css("opacity","0.7");
          $('#register').addClass('fadeInRight');
        },500);



      }, 1000);


    }

    setTimeout(openModal, 3000);
    setTimeout(hideModal, 10000);

    });

    </script -->
    <style type="text/css">
          .modal-dialog {
            height: 100vh;
            display: table;
            background: transparent;
            box-shadow: none;
            margin:0 auto;
          }
          .modal-content {
            vertical-align: middle;
            display: table-cell;
            background: transparent;
            box-shadow: none;
            border: 0;
          }
          .modal-content-wrap {
                background: transparent;
        border-radius: 3px;
        text-align: center;
          }
          .modal-content-wrap .loader-icon {
            margin-top:-45px;
          }
          .login_wrapper .panel{
    background: transparent;
          }
          .login_wrapper .login_content {
        background: rgba(255,255,255,0.8);
        border-radius: 6px;
          }
          .input__wrap {
            position: relative;
          }
          .pwd_tick_icon {
            position: absolute;
            right: 40px;
            top: 9px;
            color: green;
          }
          .pwd_remove_icon{
            position: absolute;
            right: 40px;
            top: 9px;
            color: red;
          }
          .text-left {
            text-align: left;
          }

          .text-center {
            text-align: center;
          }
          .text-right {
            text-align: right;
          }
          .forgot_pass_link_wrap {
            padding: 11px 40px 0 0;
        text-align: right;
          }
          .login_btn_wrap {
            padding-left: 28px;
            text-align: left;
          }


        </style>
   </head>
    <body class="login" ng-app="loginApp" >
        <div ng-controller="LoginController as ctrl">
          <a class="hiddenanchor" id="signup"></a>
          <a class="hiddenanchor" id="signin"></a>

          <div class="login_wrapper">
            <div id="loginPart" class="animate form login_form panel default-panel">
              <section class="login_content panel-body">
                <form id="sign-up-form" name="myForm" role="form" autocomplete="off">

                  <div class="hulk-logo">
                      <img src="../static/images/hulk_logo.png">
                  </div>

                  <h1>Login Form</h1>
                  <div>
                    <input type="email" class="form-control" placeholder="We want your username"
                        required="" ng-model="ctrl.login.username" ng-change='disableError()'/>
                  </div>
                  <div>
                    <input type="password" class="form-control" placeholder="Go ahead type in your secret key"
                        required="" ng-model="ctrl.login.pwd" ng-change='disableError()'/>
                  </div>

                  <div ng-if="checkStatus()" class="error form-group col-xs-12 no-pad">
                    <label class="text-red" ng-bind="showRes" ></label>
                  </div>

                  <div class="row">
                    <div class="col-lg-6 col-md-6 col-xs-12 login_btn_wrap">
                        <a class="btn btn-default submit" href="#" ng-click="ctrl.loginFn()" ng-disabled="myForm.$invalid">Log In</a>
                    </div>
                    <div class="col-lg-6 col-md-6 col-xs-12 forgot_pass_link_wrap">
                        <a id="reset-pwd" ng-click="ctrl.reset()" class="reset_pass" href="#" ng-disabled="myForm.$pristine">Clean up the mess</a>
                    </div>
                  </div>



                  <!--div>
                    <a class="btn btn-default submit" href="#"  ng-click="ctrl.login()" ng-disabled="myForm.$invalid">Log In</a>
                    <a id="reset-pwd" ng-click="ctrl.reset()" class="reset_pass" href="#" ng-disabled="myForm.$pristine">Clear everything</a>
                  </div-->

                  <br/>
                  <div class="clearfix"></div>

                  <div class="separator">
                    <!--p class="change_link">New to site?
                      <a href="#signup" class="to_register"> Create Account </a>
                    </p-->

                    <div class="clearfix"></div>
                    <br />

                    <div>
                      <h1><i class="fa fa-paw"></i> Hulk Fitness !</h1>
                      <p>©2016 All Rights Reserved. Developed by - www.binaryworkers.com. </p>
                    </div>
                  </div>
                </form>
              </section>
            </div>

            <div id="register" class="animate form registration_form panel default-panel">
              <section class="login_content panel-body">
                <form id="change-pwd-form" name="pwdForm" role="form" autocomplete="off">

                  <div class="hulk-logo">
                      <img src="../static/images/hulk_logo.png">
                  </div>

                  <h1>Change Password</h1>
                  <div>
                     <input type="password" ng-model="ctrl.pwd.passwd" class="form-control" placeholder="Type New Password" required="" />
                  </div>
                  <div class="input__wrap">
                     <input type="password" ng-model="ctrl.pwd.retypePwd" ng-change='matchPwd()' class="form-control" placeholder="Retype Password Again" required="" />
                     <i ng-if="showCorrect" class="glyphicon glyphicon-ok pwd_tick_icon"></i>
                     <i ng-if="showWrong" class="glyphicon glyphicon-remove pwd_remove_icon"></i>
                  </div>

                  <div ng-if="checkStatus()" class="error form-group col-xs-12 no-pad">
                    <label class="text-red" ng-bind="showRes" ></label>
                  </div>
                  <div>
                    <a class="btn btn-default submit" href="#" ng-click="ctrl.changePwd()" ng-disabled="pwdMatch">Submit</a>
                  </div>

                  <div class="clearfix"></div>

                  <div class="separator">
                    <!--p class="change_link">Already a member ?
                      <a href="#signin" class="to_register"> Log in </a>
                    </p-->

                    <div class="clearfix"></div>
                    <br />

                    <div>
                      <h1><i class="fa fa-paw"></i> Hulk Fitness !</h1>
                      <p>©2016 All Rights Reserved. Developed by - www.binaryworkers.com. </p>
                    </div>
                  </div>
                </form>
              </section>
            </div>
          </div>
        </div>
        <div id="myModal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-content-wrap">
          <img src="../static/images/balls.svg" alt="Loading..." class="loader-icon" />
          <!--div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Modal title</h4>
          </div>
          <div class="modal-body">
            <p>One fine body&hellip;</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div-->
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    </body>
</html>