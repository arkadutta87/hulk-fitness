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
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private static final String PKG_ASSOCIATED_WITH_MEMBER = "Package already associated with a memeber . Thus cannot be deleted.";
    private static final String SUCCESS = "success";
    private static final String ACCOUNT_DEACTIVATED = "The account has been deactivated. Contact your administrator";

    private static final int PAGE_COUNT = 10;
    private static final boolean sendSMSFlag = true;

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
                    //check whether the package is already associated with a member or not
                    CustomerPackageEntity obj1 = memberService.getCustomerPackageEntityFromPkgId(pkg.getId());
                    if(obj1 != null){
                        System.out.println(" ---- delete package : This package is already associated with a member thus deletion not possible. ---- ");
                        response.setCode(7);
                        response.setMessage(PKG_ASSOCIATED_WITH_MEMBER);
                    }else{
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

    @RequestMapping(value = "/index/home/member/getmembers/", method = RequestMethod.POST)
    public ResponseEntity<MemberResponse> getMembers(@RequestBody MemberRequest request) {
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

    @RequestMapping(value = "/index/home/member/read/", method = RequestMethod.POST)
    public ResponseEntity<MemberCRUDResponse> readMember(@RequestBody MemberReadDeleteRequest request) {
        System.out.println(" --- Inside read member post method ---- ");
        MemberCRUDResponse response = new MemberCRUDResponse();
        if (request == null || request.getId() <= 0 ) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            long id = request.getId();
            Member mem = memberService.getMemberFromId(id);
            if (mem == null) {
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                response.setCode(0);
                response.setMessage(SUCCESS);

                int utilization = memberService.getMemberPackageUtilizationPercentage(mem.getId());
                response.setPkg_utilization_percentage(utilization);

                DataConversion.convertMemberModelToMemberCRUDRes(mem, response);
            }
        }
        return new ResponseEntity<MemberCRUDResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/home/member/edit/", method = RequestMethod.POST)
    public ResponseEntity<MemberCRUDResponse> editMember(@RequestBody MemberAddEditRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside edit member post method ---- request -- "+request);
        MemberCRUDResponse response = new MemberCRUDResponse();
        if (request == null || request.getFirstName() == null ||
                request.getFirstName().trim().isEmpty() || request.getId() <= 0
                || request.getPinCode() == 0 || request.getCity() == null || request.getState() == null ||
                request.getLineOne() == null || request.getLineOne().trim().isEmpty()) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- edit member : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                long id = request.getId();
                Member mem = memberService.getMemberFromId(id);
                if (mem == null) {
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                }else{
                    boolean mob_flag = false;

                    String old_mobile = mem.getMobile().trim();
                    String new_mobile = request.getMobile().trim();

                    if(!old_mobile.equalsIgnoreCase(new_mobile)){
                        mob_flag = true;
                    }

                    //Member aMem = new Member();
                    //mem.setCreated_on(new Date());
                    mem.setUpdated_on(new Date());
                    mem.setUpdatedBy(user.getUsername());
                    mem.setFirstName(request.getFirstName() == null ? mem.getFirstName(): request.getFirstName());
                    mem.setLastName(request.getLastName() == null ? mem.getLastName() : request.getLastName());
                    mem.setEmail(request.getEmail() == null ? mem.getEmail() : request.getEmail());
                    mem.setMobile(request.getMobile() == null ? mem.getMobile() : request.getMobile());
                    //mem.setProfile_pic_url("/epme/static/images/picture.jpg");
                    mem.setAlternateMobile(request.getAlternateMobile() == null ? mem.getAlternateMobile() : request.getAlternateMobile());
                    String dobStr = request.getDate_of_birth() == null ? "" : request.getDate_of_birth();

                    Date dob = null;
                    if(dobStr.isEmpty()){
                        dob = mem.getDate_of_birth();
                    }else{
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");//yyyy-MM-dd HH:mm:ss.S
                        try{
                            dob=simpleDateFormat.parse(dobStr);
                        }catch (Exception e){
                            e.printStackTrace();
                            dob = new Date();
                        }
                    }
                    mem.setDate_of_birth(dob);

                    String enrollDateStr = request.getEnrollment_date() == null ? "" : request.getEnrollment_date();
                    Date enroll_date = null;
                    if(enrollDateStr.isEmpty()){
                        enroll_date = mem.getEnrollment_date();
                    }else{
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");//yyyy-MM-dd HH:mm:ss.S
                        try{
                            enroll_date=simpleDateFormat.parse(enrollDateStr);
                        }catch (Exception e){
                            e.printStackTrace();
                            enroll_date = new Date();
                        }
                    }
                    mem.setEnrollment_date(enroll_date);

                    //set the latest package expiry date to 31st Dec ,2116

                    //Address part
                    Address addr = mem.getAddress();
                    addr.setLineOne(request.getLineOne() == null ? addr.getLineOne() : request.getLineOne());
                    addr.setLineTwo(request.getLineTwo() == null ? addr.getLineTwo() : request.getLineTwo());
                    addr.setPinCode(request.getPinCode() == 0 ? addr.getPinCode() : request.getPinCode());
                    addr.setCity(request.getCity() == null ? addr.getCity() : request.getCity().toLowerCase());
                    addr.setState(request.getState() == null ? addr.getState() : request.getState().toLowerCase());
                    addr.setCountry(request.getCountry() == null ? addr.getCountry() : request.getCountry().toLowerCase());
                    //addr.setCreated_on(new Date());
                    addr.setUpdated_on(new Date());
                    addr.setUpdatedBy(user.getUsername());

                    //criss cross
                    //aMem.setAddress(addr);
                    //addr.setMember(aMem);

                    try {
                        memberService.editMember(mem);
                        memberService.editAddress(mem.getAddress());
                        DataConversion.convertMemberModelToMemberCRUDRes(memberService.getMemberFromId(mem.getId()), response);

                        ///send sms to phone number
                        String message = "Hi "+mem.getFirstName() + " " + mem.getLastName() + " you have been added as a member of Hulk Fitness Club." +
                                " Your membership number is --  "+mem.getId() + ". This mobile number have been registered with us, use this for " +
                                "further correspondence.";
                        if(mob_flag && sendSMSFlag){
                            Util.sendSMS(mem.getMobile(), message);
                        }
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

        return new ResponseEntity<MemberCRUDResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/home/member/add/", method = RequestMethod.POST)
    public ResponseEntity<MemberCRUDResponse> addMember(@RequestBody MemberAddEditRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside add member post method ---- ");
        MemberCRUDResponse response = new MemberCRUDResponse();
        if (request == null || request.getFirstName() == null ||
                request.getFirstName().trim().isEmpty() || request.getId() != 0
                || request.getPinCode() == 0 || request.getCity() == null || request.getState() == null ||
                request.getLineOne() == null || request.getLineOne().trim().isEmpty()) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- add member : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                Member aMem = new Member();
                aMem.setCreated_on(new Date());
                aMem.setUpdated_on(new Date());
                aMem.setUpdatedBy(user.getUsername());
                aMem.setFirstName(request.getFirstName() == null ? "" : request.getFirstName());
                aMem.setLastName(request.getLastName() == null ? "" : request.getLastName());
                aMem.setEmail(request.getEmail() == null ? "" : request.getEmail());
                aMem.setMobile(request.getMobile() == null ? "" : request.getMobile());
                aMem.setProfile_pic_url("/epme/static/images/picture.jpg");
                aMem.setAlternateMobile(request.getAlternateMobile() == null ? "" : request.getAlternateMobile());
                String dobStr = request.getDate_of_birth() == null ? "" : request.getDate_of_birth();

                Date dob = null;
                if(dobStr.isEmpty()){
                    dob = new Date();
                }else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");//yyyy-MM-dd HH:mm:ss.S
                    try{
                        dob=simpleDateFormat.parse(dobStr);
                    }catch (Exception e){
                        e.printStackTrace();
                        dob = new Date();
                    }
                }
                aMem.setDate_of_birth(dob);

                String enrollDateStr = request.getEnrollment_date() == null ? "" : request.getEnrollment_date();
                Date enroll_date = null;
                if(enrollDateStr.isEmpty()){
                    enroll_date = new Date();
                }else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");//yyyy-MM-dd HH:mm:ss.S
                    try{
                        enroll_date=simpleDateFormat.parse(enrollDateStr);
                    }catch (Exception e){
                        e.printStackTrace();
                        enroll_date = new Date();
                    }
                }
                aMem.setEnrollment_date(enroll_date);

                //set the latest package expiry date to 31st Dec ,2116
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.YEAR, 100);//Add 100 year

                Date aDt = c.getTime();
                aMem.setLatest_pkg_expiry(aDt);

                //Address part
                Address addr = new Address();
                addr.setLineOne(request.getLineOne() == null ? "" : request.getLineOne());
                addr.setLineTwo(request.getLineTwo() == null ? "" : request.getLineTwo());
                addr.setPinCode(request.getPinCode() == 0 ? 700120 : request.getPinCode());
                addr.setCity(request.getCity() == null ? "Kolkata".toLowerCase() : request.getCity().toLowerCase());
                addr.setState(request.getState() == null ? "west bengal" : request.getState().toLowerCase());
                addr.setCountry(request.getCountry() == null ? "india" : request.getCountry().toLowerCase());
                addr.setCreated_on(new Date());
                addr.setUpdated_on(new Date());
                addr.setUpdatedBy(user.getUsername());

                //criss cross
                aMem.setAddress(addr);
                addr.setMember(aMem);

                try {
                    memberService.saveMember(aMem);
                    DataConversion.convertMemberModelToMemberCRUDRes(aMem, response);

                    ///send sms to phone number
                    /*
                    Hi %%|name^{"inputtype" : "text", "maxlength" : "70"}%% you have been added as a member of Hulk Fitness Club. Your membership number is --
                    %%|memid^{"inputtype" : "text", "maxlength" : "30"}%%. This mobile number have been registered with us, use this for further correspondence.
                     */
                    String message = "Hi "+aMem.getFirstName() + " " + aMem.getLastName() + " you have been added as a member of Hulk Fitness Club." +
                            " Your membership number is --  "+aMem.getId() + ". This mobile number have been registered with us, use this for " +
                            "further correspondence.";
                    if(sendSMSFlag){
                        Util.sendSMS(aMem.getMobile(), message);
                    }
                    response.setCode(0);
                    response.setMessage(SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setCode(3);
                    response.setMessage(INTERNAL_SERVER_ERROR);
                }
            }
        }

        return new ResponseEntity<MemberCRUDResponse>(response, HttpStatus.OK);
    }

}
