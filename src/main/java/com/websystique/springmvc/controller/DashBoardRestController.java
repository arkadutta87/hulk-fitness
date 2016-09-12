package com.websystique.springmvc.controller;

import com.websystique.springmvc.common.TokenRedisModel;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.model.Package;
import com.websystique.springmvc.payload.*;
import com.websystique.springmvc.service.LoginService;
import com.websystique.springmvc.service.MemberService;
import com.websystique.springmvc.service.PackageService;
import com.websystique.springmvc.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class DashBoardRestController {

    @Autowired
    PackageService packageService;  //Service which will do all data retrieval/manipulation work

    @Autowired
    LoginService loginService;

    @Autowired
    MemberService memberService;

    private static final String REQUEST_DATA_ABSENT = "All fields in request not present";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error. Contact your administrator";
    private static final String DATA_NOT_PRESENT = "No data present";
    private static final String SUCCESS = "success";
    private static final String ACCOUNT_DEACTIVATED = "The account has been deactivated. Contact your administrator";

    private static final int PAGE_COUNT = 2;

    @RequestMapping(value = "/index/home/initialize/", method = RequestMethod.GET)
    public ResponseEntity<URLObjContainer> initializeURL() {
        System.out.println("Initialize URL called");
        URLObjContainer response = URLHelper.getInstance().getURLData();
        if (response == null) {
            response.setCode(1);
        }

        System.out.println("######### Data ######### - " + response);

        return new ResponseEntity<URLObjContainer>(response, HttpStatus.OK);
    }

    //adding a package
    @RequestMapping(value = "/index/home/package/add/", method = RequestMethod.POST)
    public ResponseEntity<PackageCRUDResponse> addPackage(@RequestBody PackageEditRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside add package post method ---- ");
        PackageCRUDResponse response = new PackageCRUDResponse();
        if (request == null || request.getName() == null || request.getName().trim().isEmpty() || request.getId() != 0) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- add package : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                Package pkg = new Package();
                pkg.setCreatedOn(new Date());
                pkg.setUpdatedOn(new Date());
                pkg.setUpdatedBy(user.getUsername());
                pkg.setName(request.getName() == null ? "" : request.getName());
                pkg.setSpecialName(request.getSpecialName() == null ? "" : request.getSpecialName());
                pkg.setDurationUnit(request.getDurationUnit() == null ? "" : request.getDurationUnit());
                pkg.setDurationValue(request.getDurationValue());
                pkg.setPrice(request.getPrice());
                pkg.setTrial(request.isTrial());
                pkg.setPackageDetails(request.getPackageDetails() == null ? "" : request.getPackageDetails());
                pkg.setEnabled(true);

                try {
                    packageService.savePackage(pkg);
                    DataConversion.convertPkgModelToPackageCRUDRes(pkg, response);
                    response.setCode(0);
                    response.setMessage(SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setCode(3);
                    response.setMessage(INTERNAL_SERVER_ERROR);
                }
            }
        }

        return new ResponseEntity<PackageCRUDResponse>(response, HttpStatus.OK);
    }

    //editing a package
    @RequestMapping(value = "/index/home/package/edit/", method = RequestMethod.POST)
    public ResponseEntity<PackageCRUDResponse> editPackage(@RequestBody PackageEditRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside edit package post method ---- ");
        PackageCRUDResponse response = new PackageCRUDResponse();
        if (request == null || request.getId() <= 0) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            long id = request.getId();
            Package pkg = packageService.getPackageFromId(id);

            if (pkg == null) {
                System.out.println(" ---- edit package : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

                AppUser user = loginService.getUserFromSession(sessionID);
                if (user == null) {
                    System.out.println(" ---- edit package : Possible attempt to hack in. ---- ");
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                } else {
                    pkg.setUpdatedBy(user.getUsername());
                    pkg.setUpdatedOn(new Date());
                    //pkg.setEnabled(false);

                    //copy other data in from request
                    pkg.setName(request.getName() == null ? pkg.getName() : request.getName());
                    pkg.setSpecialName(request.getSpecialName() == null ? pkg.getSpecialName() : request.getSpecialName());
                    pkg.setDurationValue(request.getDurationValue() <= 0 ? pkg.getDurationValue() : request.getDurationValue());
                    pkg.setDurationUnit(request.getDurationUnit() == null ? pkg.getDurationUnit() : request.getDurationUnit());
                    pkg.setPrice(request.getPrice() <= 0 ? pkg.getPrice() : request.getPrice());
                    pkg.setTrial(request.isTrial());
                    pkg.setPackageDetails(request.getPackageDetails() == null ? pkg.getPackageDetails() : request.getPackageDetails());
                    try {
                        packageService.updatePackage(pkg);
                        DataConversion.convertPkgModelToPackageCRUDRes(pkg, response);
                        response.setCode(0);
                        response.setMessage(SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.setCode(3);
                        response.setMessage(INTERNAL_SERVER_ERROR);
                    }
                }
            }
        }

        return new ResponseEntity<PackageCRUDResponse>(response, HttpStatus.OK);
    }

    //deleting a package
    @RequestMapping(value = "/index/home/package/delete/", method = RequestMethod.POST)
    public ResponseEntity<PackageDeleteResponse> deletePackage(@RequestBody PackageReadDeleteRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside delete package post method ---- ");
        PackageDeleteResponse response = new PackageDeleteResponse();

        if (request == null || request.getId() <= 0) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            long id = request.getId();
            Package pkg = packageService.getPackageFromId(id);

            if (pkg == null) {
                System.out.println(" ---- delete package : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

                AppUser user = loginService.getUserFromSession(sessionID);
                if (user == null) {
                    System.out.println(" ---- delete package : Possible attempt to hack in. ---- ");
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                } else {
                    pkg.setUpdatedBy(user.getUsername());
                    pkg.setUpdatedOn(new Date());
                    pkg.setEnabled(false);
                    try {
                        packageService.updatePackage(pkg);
                        response.setCode(0);
                        response.setMessage(SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.setCode(3);
                        response.setMessage(INTERNAL_SERVER_ERROR);
                    }
                }

            }
        }

        return new ResponseEntity<PackageDeleteResponse>(response, HttpStatus.OK);
    }

    //read a package
    @RequestMapping(value = "/index/home/package/read/", method = RequestMethod.POST)
    public ResponseEntity<PackageCRUDResponse> readPackage(@RequestBody PackageReadDeleteRequest request) {
        System.out.println(" --- Inside get package get method ---- ");
        PackageCRUDResponse response = new PackageCRUDResponse();

        if (request == null || request.getId() <= 0) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            long id = request.getId();
            Package pkg = packageService.getPackageFromId(id);
            if (pkg == null) {
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                response.setCode(0);
                response.setMessage(SUCCESS);

                DataConversion.convertPkgModelToPackageCRUDRes(pkg, response);
            }
        }
        return new ResponseEntity<PackageCRUDResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/index/home/package/getpackages/", method = RequestMethod.POST)
    public ResponseEntity<PackageResponse> getPackages(@RequestBody PackageRequest request) {
        System.out.println(" --- Inside get packages call ---- ");
        PackageResponse response = new PackageResponse();
        if (request == null || request.getStep() <= 0 || request.getFilters() == null) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            int step = request.getStep();
            PackageFilter filter = request.getFilters();

            PackagePaginationObject obj = packageService.getPackage(step, filter, PAGE_COUNT);
            if (obj == null) {
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                response.setCode(0);
                response.setCount((int) obj.getTotalCount());
                response.setPackages(DataConversion.convertPackageToPackagePayLoad(obj.getPackages()));
            }
        }
        return new ResponseEntity<PackageResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/home/member/getmembers/", method = RequestMethod.POST)
    public ResponseEntity<MemberResponse> getPackages(@RequestBody MemberRequest request) {
        System.out.println(" --- Inside get packages call ---- ");
        MemberResponse response = new MemberResponse();
        if (request == null || request.getStep() <= 0 || request.getFilters() == null) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            int step = request.getStep();
            MemberFilter filter = request.getFilters();

            MemberPaginationObject obj = memberService.getMember(step,filter,PAGE_COUNT);
            if (obj == null) {
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                response.setCode(0);
                response.setCount((int) obj.getTotalCount());
                response.setMembers(DataConversion.convertMemberToMemberPayLoad(obj.getPackages()));
            }

        }
        return new ResponseEntity<MemberResponse>(response, HttpStatus.OK);
    }

    //change password functionality after logging in
    @RequestMapping(value = "/index/home/changePassword/", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> loginAppUser(@RequestBody ChangePasswordRequestLogged request,HttpServletRequest request2) {

        GenericResponse response = new GenericResponse();
        if (request == null || request.getNewPassword() == null) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String pwd = request.getNewPassword();
            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- change password : logged-in user : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                if (!user.isactive()) {
                    response.setCode(2);
                    response.setMessage(ACCOUNT_DEACTIVATED);
                } else {
                    String encypPwd = Util.encrypt(pwd);
                    user.setUpdated_on(new Date());
                    user.setPassword(encypPwd);
                    //user.setIsotp(false);

                    loginService.updateUser(user);
                    response.setCode(0);
                    response.setMessage(SUCCESS);
                }
            }
        }
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }


}
