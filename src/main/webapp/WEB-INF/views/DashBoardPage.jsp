<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Hulk Fitness!</title>
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value='/static/images/hulk_logo.png' />" />

<!-- Bootstrap -->
<link
	href="<c:url value='/static/vendors/bootstrap/dist/css/bootstrap.min.css' />"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="<c:url value='/static/vendors/font-awesome/css/font-awesome.min.css' />"
	rel="stylesheet">
<!-- NProgress -->
<link href="<c:url value='/static/vendors/nprogress/nprogress.css' />"
	rel="stylesheet">
<!-- jQuery custom content scroller -->
<link
	href="<c:url value='/static/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css' />"
	rel="stylesheet" />

<!-- Custom Theme Style -->
<link href="<c:url value='/static/css/dashboard.min.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/static/vendors/bootstrap-daterangepicker/daterangepicker.css' />"
	rel="stylesheet">
<!-- Ion.RangeSlider -->
    <link href="<c:url value='/static/vendors/normalize-css/normalize.css' />"  rel="stylesheet">
    <link href="<c:url value='/static/vendors/ion.rangeSlider/css/ion.rangeSlider.css' />"  rel="stylesheet">
    <link href="<c:url value='/static/vendors/ion.rangeSlider/css/ion.rangeSlider.skinFlat.css' />"  rel="stylesheet">
    <link href="<c:url value='/static/vendors/ng-datepicker/src/css/ngDatepicker.css' />"  rel="stylesheet">

<style type="text/css">
.modal-dialog {
	height: 100vh;
	display: table;
	background: transparent;
	box-shadow: none;
	margin: 0 auto;
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
	margin-top: -45px;
}
</style>
<!-- Custom Theme Scripts -->

<script
	src="<c:url value='/static/vendors/jquery/dist/jquery.min.js' />"></script>
<script src="http://code.jquery.com/mobile/2.2.4/jquery.mobile-2.2.4.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-cookies.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="<c:url value='/static/vendors/moment/moment.js' />"></script>
<script
	src="<c:url value='/static/vendors/ng-datepicker/src/js/ngDatepicker.js' />"></script>
<script src="<c:url value='/static/js/home.js' />"></script>
<script src="<c:url value='/static/js/controller/home_controller.js' />"></script>
<script src="<c:url value='/static/js/factory/home_factory.js' />"></script>

<!-- Jquery-SmartWizard -->
<script src="<c:url value='/static/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js' />"></script>
<script
	src="<c:url value='/static/vendors/bootstrap-daterangepicker/daterangepicker.js' />"></script>
<script>
    $(document).ready(function () {
        $('#myModal').modal('show');
    });
</script>


</head>

<body class="nav-md" ng-app="homeApp">
	<div class="container body" ng-controller="HomeController as ctrl">
		<div class="main_container">
			<div class="col-md-3 left_col menu_fixed pg_left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="#" class="site_title"><i class="fa fa-paw"></i> <span>Hulk
								Fitness!</span></a>
					</div>

					<div class="clearfix"></div>

					<!-- menu profile quick info -->
					<div class="profile">
						<div class="profile_pic">
							<img src="../../static/images/user.png" alt="User Photo"
								class="img-circle profile_img">
						</div>
						<div class="profile_info">
							<span>Welcome,</span>
							<h2>${user_name}</h2>
						</div>
					</div>
					<!-- /menu profile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<div class="menu_section">
							<h3>General</h3>
							<ul class="nav side-menu">
								<li>
								    <a class="js_home_menu_link">
                                        <i class="fa fa-home"></i>
                                        Home
                                        <span class="fa fa-chevron-down"></span>
								    </a>
									<ul class="nav child_menu">
										<li><a href="#">Dashboard</a></li>
										<li><a href="#">Dashboard2</a></li>
									</ul>
								</li>
								<li>
                                    <a class="js_conf_menu_link">
                                        <i class="fa fa-edit"></i>
                                        Configuration
                                        <span class="fa fa-chevron-down"></span>
                                    </a>
									<ul class="nav child_menu">
										<li class="js_packages_page_link"><a href="#" ng-click="ctrl.clickURLFn(2)" > Packages </a></li>
										<li class="js_members_page_link"><a href="#" ng-click="ctrl.clickURLFn(3)"> Members </a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
					<!-- /sidebar menu -->

					<!-- /menu footer buttons -->
					<!-- /menu footer buttons -->
				</div>
			</div>



			<!-- top navigation -->
			<div class="top_nav navbar navbar-default navbar-fixed-top pg_top_nav">
				<div class="nav_menu">
					<nav>
						<div class="nav toggle col-lg-3 col-md-3 col-xs-3">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>

						<div class="col-lg-6 col-md-6 col-xs-6 header_breadcrumb">

						    <ul class="breadcrumb breadcrumb_custom">
						        <li ng-cloak ng-repeat="aCrumb in crumbList" ng-class="isLast(aCrumb.id)?'active' : ''">
                                   <a ng-if="!isLast(aCrumb.id)" ng-click="ctrl.brcrClick(aCrumb.id)" href="#">{{aCrumb.value}}</a>
                                   <span ng-if="isLast(aCrumb.id)" >{{aCrumb.value}}</span>
						        </li>
                            </ul>

						</div>



						<ul class="nav navbar-nav navbar-right col-lg-3 col-md-3 col-xs-3">
							<li class="">
							<a href="javascript:;"
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <img
									src="../../static/images/user.png" alt="">${user_name} <span
									class=" fa fa-angle-down"></span>
							</a>
								<ul class="dropdown-menu dropdown-usermenu pull-right">
									<li><a href="#" ng-click="ctrl.pwdFn()"> Change Password </a></li>
									<li><a href="#" ng-click="ctrl.logoutFn()"><i
											class="fa fa-sign-out pull-right"></i> Log Out</a></li>
								</ul>
							</li>
						</ul>

					</nav>
				</div>
			</div>
			<!-- /top navigation -->

			<!-- page content -->
			<div ng-include="currentPage"></div>
			<!-- /page content -->

			<!-- footer content -->
			<div class="row">
				<footer>
					<div class="pull-right">
						Hulk Fitness - Admin Interface - powered by by
						<bold>www.binaryworkers.com<bold>
					</div>
					<div class="clearfix"></div>
				</footer>
			</div>

			<!-- /footer content -->
		</div>
	</div>

	<div id="myModal" class="modal fade " tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-content-wrap">
					<img src="../../static/images/balls.svg" alt="Loading..."
						class="loader-icon" />
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- scripts -->
	<!-- jQuery -->

	<!-- Bootstrap -->
	<script
		src="<c:url value='/static/vendors/bootstrap/dist/js/bootstrap.min.js' />"></script>
	<!-- FastClick -->
	<script
		src="<c:url value='/static/vendors/fastclick/lib/fastclick.js' />"></script>
	<!-- NProgress -->
	<script src="<c:url value='/static/vendors/nprogress/nprogress.js' />"></script>
	<!-- jQuery custom content scroller -->
	<script
		src="<c:url value='/static/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js'/>"></script>
	<script src="<c:url value='/static/js/custom.min.js'/>"></script>


</body>
</html>