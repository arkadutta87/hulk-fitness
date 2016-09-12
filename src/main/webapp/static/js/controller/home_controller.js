'use strict';

angular.module('homeApp').controller('HomeController', ['$scope', '$cookies', '$timeout', '$interval','HomeFactory',
    function($scope, $cookies, $timeout, $interval, HomeFactory) {

        $scope.currentPage = "";// "../../static/html/firstPage.html";// "/epme/static/html/index.html";

        var self = this;
        self.logoutFn = logout;
        self.profileFn = profile;
        self.pwdFn = pwd;
        self.clickURLFn = clickURL;
        self.brcrClick = brcrClickFn;

        $scope.crumbList = [];
        var pkgCurrentPage = 1;
        $scope.pkgCount = 0;
        $scope.pkPaginationStrt = 0;
        $scope.pkPaginationEnd = 0;
        var pkgStep = 2;
        $scope.packagePaginationList = [];

        $scope.brMap = new Map();
        $scope.secMap = new Map();
        $scope.packageList = [];

        $scope.searchPkg = false;

        $scope.aErr ={};
        $scope.aErr['flagP'] = false;
        $scope.aErr['msg']= "";


        var initPkgSearch = function(){
            $scope.pkgObject = {};
            $scope.pkgObject['step'] = 1;
            $scope.pkgObject['filters'] = {};
            $scope.pkgObject.filters['searchStr'] = "";
            $scope.pkgObject.filters['duration'] = {};
            $scope.pkgObject.filters.duration['unit'] = "";
            $scope.pkgObject.filters.duration['value'] = -1;

            $scope.packagePaginationList = [];
            pkgCurrentPage = 1;
        }

        var fixPkgPagnStrEnd = function(){
            $scope.pkPaginationStrt = (pkgCurrentPage - 1)* pkgStep + 1;
            $scope.pkPaginationEnd = pkgCurrentPage * pkgStep;

            if($scope.pkPaginationEnd > $scope.pkgCount ){
                $scope.pkPaginationEnd = $scope.pkgCount;
            }
        }

        var setUpPkgPagination = function(){
            var i = -1;
            if($scope.pkgCount == 0){
               i = 0;
               $scope.pkPaginationStrt = 0;
               $scope.pkPaginationEnd = 0;
            }
            else if($scope.pkgCount % pkgStep == 0){
                i = $scope.pkgCount / pkgStep;
                fixPkgPagnStrEnd();
            }else{
                i = ($scope.pkgCount / pkgStep) + 1;
                fixPkgPagnStrEnd();
            }
            $scope.packagePaginationList = [];
            for(var j = 1 ; j <= i ; j++){
                var o1 = {};
                o1['id'] = j;

                $scope.packagePaginationList.push(o1);

            }

            //pkgCurrentPage = 1;
        }

        $scope.isPaginationActive = function(id){
            if(pkgCurrentPage == id)
                return true;
            else
                return false;
        }

        /*  Change password funcitonality */

        var initPwdPart = function(){
            $scope.changePwd = {};
            $scope.showCorrect = false;
            $scope.showWrong = false;
            $scope.pwdMatch = true;
        }

        function pwd(){
           $('#myModal').modal('show');
           //$scope.currentPage = "../../static/html/c.html";
           $scope.crumbList = [];
           $scope.crumbList.push({'id':6,'value':'Change Password'});
           initPwdPart();

           $scope.currentPage = $scope.map[6];
           setTimeout(function() {
               $('#myModal').modal('hide');
           },1000);
        }

        $scope.addPwdChange = function(){

            if ($scope.changePwd.pwd1 != '' && $scope.changePwd.pwd1 == $scope.changePwd.pwd2) {
                $scope.showCorrect = true;
                $scope.showWrong = false;
                $scope.pwdMatch = false;
            } else {
                $scope.showWrong = true;
                $scope.pwdMatch = true;
                $scope.showCorrect = false;
            }

        }

        $scope.cancelPwd = function(){
            initPwdPart();
            self.brcrClick(2);
        }

        $scope.resetPwd = function(){
           initPwdPart();
        }

        $scope.submitPwd = function(){
            console.log(JSON.stringify($scope.changePwd));
            $('#myModal').modal('show');

            var obj = {};
            obj['newPassword'] = $scope.changePwd.pwd1.trim();

            HomeFactory.changePassword(obj)
                .success(function(data){
                    //create the map and assign the first basic -- for this create a function
                    var code = data.code;
                    if(code != 0){
                        $('#myModal').modal('hide');
                        alert("The password change failed . Please contact Admin.");
                    }else{
                        initPwdPart();
                        initPage(2);
                        //alert("The Package with id - "+id + " deleted");
                        $scope.aErr.msg = "The password have been changed successfull ";
                        $scope.aErr.flagP = true;
                        $scope.currentPage = $scope.map[2];

                        setTimeout(function() {
                            $scope.aErr['flagP'] = false;
                            $scope.aErr['msg']= "";
                            $scope.$apply();
                        },2000);

                    }

                })
                .error(function(error) {
                    console.log("The password change failed . Please contact Admin.");
                    $('#myModal').modal('hide');
                    alert("The password change failed . Please contact Admin.");
                }).finally(function(){
                    console.log('changePassword method call finished');
                });

        }
        /*  Change password funcitonality */

        /*
        Package Pagination code
        */

        $scope.paginate =  function(id){
            pkgCurrentPage = id;
            $scope.pkgObject['step'] = pkgCurrentPage;

            $('#myModal').modal('show');
            callPkgs();
        }

        $scope.isPkgPrevNextDisabled = function(str){
            if(str == 'previous'){
                if(pkgCurrentPage <= 1)
                {
                    return true;
                }else{
                    return false;
                }
            }else if(str == 'next'){
                if(pkgCurrentPage * pkgStep >= $scope.pkgCount ){
                    return true;
                }else{
                    return false;
                }
            }else
                return false;

        }

        $scope.pkg_prev_next = function(str){
            if(str == 'previous')
                $scope.paginate(pkgCurrentPage-1);
            else if(str == 'next')
                $scope.paginate(pkgCurrentPage+1);
        }

        /*
        Package Pagination code
        */

        /* Package Edit code */
        $scope.pkgEdit = function(id,name){
            $('#myModal').modal('show');
            console.log("Package Clicked -- id - "+id  +" , name - "+name );

            var obj = {'id' : id};
            HomeFactory.readPackage(obj)
                .success(function(data){
                    //create the map and assign the first basic -- for this create a function
                    var code = data.code;
                    if(code != 0){
                        $scope.editPkgObj = {};
                        $('#myModal').modal('hide');
                        alert("The package with name - "+name + " couldnot be fetched. Please contact Admin.");
                    }else{
                        //initPage(2);
                        //alert("The Package with id - "+id + " deleted");
                        //$scope.aErr.msg = "The Package with id - "+id + " deleted";
                        //$scope.aErr.flagP = true;
                        $scope.crumbList = [];
                        $scope.crumbList.push({'id':1,'value':'Configuration'},{'id':2,'value':'Packages'},{'id':4,'value':name});
                        populatePackage(data);
                        $scope.currentPage = $scope.map[4];
                        setTimeout(function() {
                            $('#myModal').modal('hide');
                        },1000);

                    }

                })
                .error(function(error) {
                    console.log("The package couldnot be fetched. Please contact Admin.");
                    $('#myModal').modal('hide');
                    alert("The package with name - "+name + " couldnot be fetched. Please contact Admin.");
                }).finally(function(){
                    console.log('pkgEdit method call finished');
                });
        }

        var populatePackage = function(dt){
            $scope.editPkgFlag = false;
            $scope.editPkgObj = {};
            $scope.editPkgObj['id'] = dt.id;
            $scope.editPkgObj['name'] = dt.name;
            $scope.editPkgObj['specialName'] = dt.specialName;
            $scope.editPkgObj['durationUnit'] = dt.durationUnit;
            if(dt.durationUnit.trim().toUpperCase() == 'MONTHS' ){
                $scope.editPkgObj['durationUnitRd'] = 1;
            }else{
                $scope.editPkgObj['durationUnitRd'] = 2;
            }
            $scope.editPkgObj['durationValue'] = dt.durationValue;
            $scope.editPkgObj['price'] = dt.price;
            $scope.editPkgObj['packageDetails'] = dt.packageDetails;
            $scope.editPkgObj['isTrial'] = dt.trial;

        }

        var populateSubmitEditObject = function(obj){
            obj['id'] = $scope.editPkgObj.id;
            obj['specialName'] = $scope.editPkgObj.specialName;
            obj['name'] = $scope.editPkgObj.name;
            if($scope.editPkgObj.durationUnitRd == 1 ){
                obj['durationUnit']= 'MONTHS';
            }else{
                obj['durationUnit']= 'DAYS';
            }
            obj['durationValue'] = $scope.editPkgObj['durationValue'];
            obj['price'] = $scope.editPkgObj['price'] ;
            obj['packageDetails'] = $scope.editPkgObj['packageDetails'];
            obj['trial'] = $scope.editPkgObj['isTrial'];
        }

        $scope.cancelPkgEdit = function(){
           $scope.editPkgObj = {};
           self.brcrClick(2);
        }

        $scope.pkgDeleteEdit = function(id){
            $scope.pkgDelete(id);
            $scope.currentPage = $scope.map[2];
        }

        $scope.editPkgChange = function(){
            $scope.editPkgFlag = true;
        }

        $scope.resetPkgEdit = function(id,name){
            $scope.pkgEdit(id,name);
        }

        $scope.submitPkgEdit = function(id,name){
            console.log(JSON.stringify($scope.editPkgObj));
            $('#myModal').modal('show');
            var obj = {};
            populateSubmitEditObject(obj);

            HomeFactory.editPackage(obj)
                .success(function(data){
                    //create the map and assign the first basic -- for this create a function
                    var code = data.code;
                    if(code != 0){
                        $scope.editPkgObj = {};
                        $('#myModal').modal('hide');
                        alert("The package with name - "+ name + " couldnot be updated. Please contact Admin.");
                    }else{
                        //initPage(2);
                        //alert("The Package with id - "+id + " deleted");
                        //$scope.aErr.msg = "The Package with id - "+id + " deleted";
                        //$scope.aErr.flagP = true;
                        $scope.crumbList = [];
                        $scope.crumbList.push({'id':1,'value':'Configuration'},{'id':2,'value':'Packages'},{'id':4,'value':data.name});
                        populatePackage(data);
                        $scope.currentPage = $scope.map[4];
                        setTimeout(function() {
                            $('#myModal').modal('hide');
                        },1000);

                    }

                })
                .error(function(error) {
                    console.log("The package couldnot be updated. Please contact Admin.");
                    $('#myModal').modal('hide');
                    alert("The package with name - "+ name + " couldnot be updated. Please contact Admin.");
                }).finally(function(){
                    console.log('submitPkgEdit method call finished');
                });
        }

        /* Package Edit code */

        /* Package Add code */

        var populateSubmitAddObject = function(obj){
            obj['id'] = 0;
            obj['specialName'] = $scope.addPkgObj.specialName;
            obj['name'] = $scope.addPkgObj.name;
            if($scope.addPkgObj.durationUnitRd == 1 ){
                obj['durationUnit']= 'MONTHS';
            }else{
                obj['durationUnit']= 'DAYS';
            }
            obj['durationValue'] = $scope.addPkgObj['durationValue'];
            obj['price'] = $scope.addPkgObj['price'] ;
            obj['packageDetails'] = $scope.addPkgObj['packageDetails'];
            obj['trial'] = $scope.addPkgObj['isTrial'] ? $scope.addPkgObj['isTrial'] : false ;
        }

        $scope.addPkg = function(){
            $('#myModal').modal('show');
            $scope.addPkgObj = {};
            $scope.addPkgFlag = false;
            console.log("Package Add Clicked " );
            $scope.crumbList = [];
            $scope.crumbList.push({'id':1,'value':'Configuration'},{'id':2,'value':'Packages'},{'id':5,'value':'Add Package'});
            $scope.currentPage = $scope.map[5];
            setTimeout(function() {
                $('#myModal').modal('hide');
            },1000);
        }

        $scope.addPkgChange = function(){
            $scope.addPkgFlag = true;
        }

        $scope.resetPkgAdd = function(){
            $('#myModal').modal('show');
            $scope.addPkgObj = {};
            $scope.addPkgFlag = false;
            setTimeout(function() {
                $('#myModal').modal('hide');
            },700);
        }

        $scope.cancelPkgAdd = function(){
            $scope.addPkgObj = {};
            $scope.addPkgFlag = false;
            self.brcrClick(2);
        }

        $scope.submitPkgAdd = function(id){
            console.log(JSON.stringify($scope.addPkgObj));
            if($scope.addPkgObj.name && $scope.addPkgObj.specialName && $scope.addPkgObj.durationValue
                && $scope.addPkgObj.durationUnitRd && $scope.addPkgObj.price && $scope.addPkgObj.packageDetails)
                {

                $('#myModal').modal('show');
                var obj = {};
                populateSubmitAddObject(obj);

                HomeFactory.addPackage(obj)
                    .success(function(data){
                        //create the map and assign the first basic -- for this create a function
                        var code = data.code;
                        if(code != 0){
                            //$scope.addPkgObj = {};
                            $('#myModal').modal('hide');
                            alert("The package with name - couldnot be added. Please contact Admin.");
                        }else{
                            //initPage(2);
                            //alert("The Package with id - "+id + " deleted");
                            //$scope.aErr.msg = "The Package with id - "+id + " deleted";
                            //$scope.aErr.flagP = true;
                            initPage(2);
                            //alert("The Package with id - "+id + " deleted");
                            $scope.aErr.msg = "The Package with name - "+data.name + " created";
                            $scope.aErr.flagP = true;
                            $scope.currentPage = $scope.map[2];

                            setTimeout(function() {
                                $scope.aErr['flagP'] = false;
                                $scope.aErr['msg']= "";
                                $scope.$apply();
                            },2000);

                        }

                    })
                    .error(function(error) {
                        console.log("The package couldnot be fetched. Please contact Admin.");
                        $('#myModal').modal('hide');
                        alert("The package with name - "+name + " couldnot be fetched. Please contact Admin.");
                    }).finally(function(){
                        console.log('pkgEdit method call finished');
                    });


            }else{
                alert('---  Kindly fill all the fields, before clicking on submit -- ');
            }
        }

        /* Package Add code */

        /*Package Delete code*/

        $scope.pkgDelete = function(id){
            console.log("Delete Package with id - "+ id);
            $('#myModal').modal('show');
            var obj = {};
            obj['id'] = id;
            HomeFactory.deletepackage(obj)
                .success(function(data){
                    //create the map and assign the first basic -- for this create a function
                    var code = data.code;
                    if(code != 0){
                        $('#myModal').modal('hide');
                        alert("The package couldnot be deleted. Please contact Admin.");
                    }else{
                        initPage(2);
                        //alert("The Package with id - "+id + " deleted");
                        $scope.aErr.msg = "The Package with id - "+id + " deleted";
                        $scope.aErr.flagP = true;

                        setTimeout(function() {
                            $scope.aErr['flagP'] = false;
                            $scope.aErr['msg']= "";
                            $scope.$apply();
                        },2000);

                    }

                })
                .error(function(error) {
                    console.log("The package couldnot be deleted. Please contact Admin.");
                    $('#myModal').modal('hide');
                    alert("The package couldnot be deleted. Please contact Admin.");
                }).finally(function(){
                    console.log('pkgDelete method call finished');
                });

        }

        /*Package Delete code*/


        var callPkgs = function(){
            HomeFactory.getpackages($scope.pkgObject)
                .success(function(data){
                    //create the map and assign the first basic -- for this create a function
                    var code = data.code;
                    if(code != 0){
                        $('#myModal').modal('hide');
                        alert("Be prepared nothing will work. Please contact Admin.");
                    }else{
                        $scope.packageList  = data.packages;
                        $scope.pkgCount = data.count;
                        setUpPkgPagination();
                        setTimeout(function() {
                            $('#myModal').modal('hide');
                        },500);
                    }

                })
                .error(function(error) {
                    console.log("Error fetching the package list");
                    $('#myModal').modal('hide');
                    alert("Be prepared nothing will work. Please contact Admin.");
                }).finally(function(){
                    console.log('callPkgs method call finished');
                });
        }

        var getPkgs = function(){
            $scope.searchPkg = false;
            initPkgSearch();
            $('#myModal').modal('show');

            callPkgs();
        }

        $scope.isOddPkg = function(id){
            var elementPos = $scope.packageList.map(function(x) {return x.id; }).indexOf(id);
            if(elementPos % 2 == 0){
                return false;
            }else{
                return true;
            }
        }


        $scope.pkgSearch = function(){
            initPkgSearch();
            var searchStr = $('#pkg1').val();
            console.log("The value of search String - "+searchStr);

            var selVal = $( "#pkg2" ).val();
            console.log("The value of duration unit - "+selVal);

            var durVal = $('#pkg3').val();
            console.log("The value of duration val - "+durVal);

            var flag = false;

            if( $scope.searchPkg || (searchStr && searchStr != '') || (selVal >= 0 && durVal  )){
                flag = true;
                if(!((searchStr && searchStr != '') || (selVal >= 0 && durVal  ))){
                    $scope.searchPkg = false;
                }else{
                    $scope.searchPkg = true;
                }
            }

            if(flag){
                $scope.pkgObject.filters['searchStr'] = searchStr;
                if(selVal >= 0 && durVal ){
                    $scope.pkgObject.filters.duration['value'] = durVal;
                    var unit = "MONTHS";
                    if(selVal == 1){
                        unit = "DAYS";
                    }

                    $scope.pkgObject.filters.duration['unit'] = unit;
                }
                $('#myModal').modal('show');
                callPkgs();
            }
        }

        var breadcrumsSetup = function(){

            var obj1 = {};
            obj1['inculdeId'] = 2;

            var arr1 = [];
            arr1.push({'id':1,'value':'Configuration'},{'id':2,'value':'Packages'});
            obj1['brId'] = 2;
            $scope.secMap.set(1,obj1);
            $scope.secMap.set(2,obj1);
            $scope.brMap.set(2, arr1);

            obj1 = {};
            obj1['inculdeId'] = 3;



            var arr2 = [];
            arr2.push({'id':1,'value':'Configuration'},{'id':3,'value':'Members'});
            obj1['brId'] = 3;
            $scope.brMap.set(3, arr2);
            $scope.secMap.set(3,obj1);


        }

        var setbrList = function(id){
            $scope.crumbList = $scope.brMap.get(id);
        }

        $scope.isLast= function(id){
            var elementPos = $scope.crumbList.map(function(x) {return x.id; }).indexOf(id);
            if(elementPos == $scope.crumbList.length-1)
            {
                return true;
            }else{
                return false;
            }
        }

        var firstClick = function(){
            //$scope.crumbList.push({'id':1,'value':'Configuration'},{'id':2,'value':'Packages'});
            breadcrumsSetup();
            setbrList(2);

            getPkgs();

            $scope.currentPage = $scope.map[2];
            setTimeout(function() {
                $('.js_conf_menu_link').trigger('click');
                $('.js_conf_menu_link').parent().find('ul:first > li').removeClass('active')
                $('.js_packages_page_link').addClass('active');
                $('#myModal').modal('hide');
            },1000);


        }


        $scope.init = function(){

            HomeFactory.initializeURI()
            	.success(function(data){
                    //create the map and assign the first basic -- for this create a function
                    var code = data.code;
                    if(code != 0){
                        $('#myModal').modal('hide');
                        alert("Be prepared nothing will work. Please contact Admin.");
                    }else{
                        var urlArr = data.values;
                        $scope.map = new Object(); // or var map = {};
                        //map[myKey1] = myObj1;
                        //map[myKey2] = myObj2;
                        for (var i = 0; i < urlArr.length; i++) {
                            var aObj = urlArr[i];
                            $scope.map[aObj.id] = aObj.url;

                        }

                        firstClick();

                    }

            	})
            	.error(function(error) {
                    console.log("Error initilizing the url map");
                    $('#myModal').modal('hide');
                    alert("Be prepared nothing will work. Please contact Admin.");
            	}).finally(function(){
            	    console.log('some exception happened');
            	});
        }

        var initPage = function(id){
            if(id == 1 || id == 2){
                //$scope.pkgSearch();
                //clear all search boxes
                $('#pkg1').val("");
                $( "#pkg2" ).val(-1);
                $( "#pkg3" ).val("");
                getPkgs();
           }
        }

        function brcrClickFn(id){
           $('#myModal').modal('show');
           var obj = $scope.secMap.get(id);
           setbrList(obj['brId']);
           $scope.currentPage = $scope.map[obj['inculdeId']];
           initPage(id);
           //$scope.crumbList = [];
           setTimeout(function() {
               $('#myModal').modal('hide');
           },1000);
        }

        function clickURL(id){
           $('#myModal').modal('show');
           setbrList(id);
           $scope.currentPage = $scope.map[id];

           initPage(id);
           setTimeout(function() {
               $('#myModal').modal('hide');
           },1000);
        }

        function profile(){
           $('#myModal').modal('show');
           $scope.currentPage = "../../static/html/c.html";
           setTimeout(function() {
               $('#myModal').modal('hide');
           },1000);
        }

        function logout() {
            // start the modal
            $('#myModal').modal('show');
            var tokenStr = $cookies.get('epme_session');
            if(tokenStr == null ||  tokenStr == ''){
                // log this guy out
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
                        // $scope.status = true;
                        // $scope.showRes = "Internal Server Error. Contact
						// Administrator";
                });
            }
        }

        $scope.init();

    }
]);