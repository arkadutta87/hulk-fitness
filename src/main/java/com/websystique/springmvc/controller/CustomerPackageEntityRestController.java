package com.websystique.springmvc.controller;

import com.websystique.springmvc.common.PackageMemberEntityException;
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
public class CustomerPackageEntityRestController {

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

    private static final int PAGE_COUNT_SEC = 10;
    private static final boolean sendSMSFlag = true;

    @RequestMapping(value = "/index/home/customerpackageentity/list/", method = RequestMethod.POST)
    public ResponseEntity<CustomerPackageEntityListResponse> listCustomerPackageEntity(@RequestBody CustomerPackageEntityListRequest request) {
        System.out.println(" --- Inside list customerpackageentity post method ---- ");
        CustomerPackageEntityListResponse response = new CustomerPackageEntityListResponse();
        if (request == null || request.getId() <= 0 || request.getStep() <= 0) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            long id = request.getId();
            int step = request.getStep();
            CustomerPackageEntityPaginationObject obj = memberService.getCustomerPackageEntityList(id, step, PAGE_COUNT_SEC);
            //Member mem = memberService.getMemberFromId(id);
            if (obj == null) {
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                response.setCode(0);
                response.setMessage(SUCCESS);

                response.setCode(0);
                response.setCount((int) obj.getTotalCount());
                response.setPackages(obj.getaList());

            }
        }
        return new ResponseEntity<CustomerPackageEntityListResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/home/customerpackageentity/delete/", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> deleteCustomerPackageEntity(@RequestBody CustomerPackageEntityDeleteRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside delete customerpackageentity post method ---- ");
        GenericResponse response = new GenericResponse();
        if (request == null || request.getId() <= 0) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- delete customerpackageentity : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                long id = request.getId();
                CustomerPackageEntity obj = memberService.getCustomerPackageEntityFromId(id);
                //Member mem = memberService.getMemberFromId(id);
                if (obj == null || !obj.isEnabled()) {
                    System.out.print("The object with id doesnot exist or is disabled. Possible attempt to hack in.");
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                } else {
                /*
                1. Get the meber associated with it.
                2. Get the date that need to be associated with the member
                3. update the package-entity association along with the member with expiration date
                 */
                    long memId = obj.getMember_id();
                    Member aMem = memberService.getMemberFromId(memId);
                    Date dt = memberService.getExpiryDateAssociatedWithMember(memId, obj.getId());

                    obj = memberService.getCustomerPackageEntityFromId(id);
                    aMem = memberService.getMemberFromId(memId);

                    obj.setEnabled(false);
                    obj.setUpdated_on(new Date());
                    obj.setUpdatedBy(user.getUsername());

                    aMem.setLatest_pkg_expiry(dt);
                    aMem.setUpdated_on(new Date());
                    aMem.setUpdatedBy(user.getUsername());

                    memberService.editMember(aMem);
                    memberService.editCustomerPackageEntity(obj);

                    response.setCode(0);
                    response.setMessage(SUCCESS);

                }
            }


        }
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/index/home/customerpackageentity/add/", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> addCustomerPackageEntity(@RequestBody CustomerPackageEntityAddRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside add customerpackageentity post method ---- ");
        GenericResponse response = new GenericResponse();
        if (request == null || request.getMember_id() <= 0
                || request.getPackage_id() <= 0 || request.getPackage_enrollment_date() == null
                || request.getPackage_enrollment_date().isEmpty()) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {

            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- add customerpackageentity : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {

                long pkgId = request.getPackage_id();
                long memId = request.getMember_id();

                Member aMem = memberService.getMemberFromId(memId);
                Package aPkg = packageService.getPackageFromId(pkgId);
                //CustomerPackageEntity obj = memberService.getCustomerPackageEntityFromId(id);
                //Member mem = memberService.getMemberFromId(id);
                if (aMem == null || aPkg == null || !aPkg.isEnabled()) {
                    System.out.print("The object with member id or package id is invalid. Possible attempt to hack in.");
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                } else {
                /*
                1. Extract the package duration unit and duration value
                2. Extract the package price
                3. calculate the expiry date and the final price by  using the top_up_time and discount_percentage
                 */
                    CustomerPackageEntity entity = new CustomerPackageEntity();

                    String phoneNumber = aMem.getMobile();

                    double price1 = aPkg.getPrice();
                    double discount_percentage = request.getDiscount_percentage();

                    //final price
                    double price2 = Util.calculatePrice(price1, discount_percentage);

                    //expiration_date calculation
                    String durationUnit = aPkg.getDurationUnit();
                    double durationValue = aPkg.getDurationValue();

                    String enrollmentDateStr = request.getPackage_enrollment_date();
                    int topUpDays = request.getTop_up_time();

                    Date expirationDate = null;

                    try {
                        expirationDate = Util.calculatePkgMemEntityExpirationDate(durationUnit, durationValue,
                                enrollmentDateStr, topUpDays);
                    } catch (PackageMemberEntityException e) {
                        System.out.println(e);
                    }

                    if (expirationDate == null) {
                        response.setCode(6);
                        response.setMessage(INTERNAL_SERVER_ERROR);
                    } else {
                        //save package entity
                        CustomerPackageEntity custObj = new CustomerPackageEntity();
                        custObj.setEnabled(true);
                        custObj.setCreated_on(new Date());
                        custObj.setUpdated_on(new Date());
                        custObj.setUpdatedBy(user.getUsername());
                        custObj.setDiscount_percentage(discount_percentage);
                        custObj.setFinal_price(price2);
                        custObj.setMember_id(memId);
                        custObj.setPackage_id(pkgId);
                        custObj.setTop_up_time(topUpDays);

                        Date dt = null;


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        try {
                            dt = simpleDateFormat.parse(enrollmentDateStr);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //throw new PackageMemberEntityException(e.getMessage());
                        }
                        custObj.setPackage_enrollment_date(dt);
                        custObj.setPackage_expiry_date(expirationDate);

                        memberService.saveCustomerPackageEntity(custObj);

                        System.out.println("Expiration date --- #### -- "+expirationDate);
                        Date expiryDt = aMem.getLatest_pkg_expiry();
                        if (Util.printDateDiff(expirationDate, expiryDt) < 0) {
                            //update memeber
                            aMem.setLatest_pkg_expiry(expirationDate);
                            aMem.setUpdated_on(new Date());
                            aMem.setUpdatedBy(user.getUsername());
                            memberService.editMember(aMem);
                        }

                        String message = "Hi "+aMem.getFirstName() + " " + aMem.getLastName() + " esteemed member of Hulk Fitness Club." +
                                " You have opted for the package --  "+aPkg.getName() + ". Your package enrollment date is -- "
                                +simpleDateFormat.format(dt) + " and expiry date is --  "+simpleDateFormat.format(expirationDate);
                        if(sendSMSFlag){
                            Util.sendSMS(phoneNumber, message);
                        }

                        response.setCode(0);
                        response.setMessage(SUCCESS);
                    }

                }

            }

        }
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/index/home/memberpackageprogressentity/add/", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> addMemberPackageProgressEntity(@RequestBody MemberPackageProgressEntityAddRequest request, HttpServletRequest request2) {
        System.out.println(" --- Inside add MemberPackageProgressEntity post method ---- ");
        GenericResponse response = new GenericResponse();
        if (request == null || request.getPackage_mem_entity_id() <= 0 || request.getText() == null
                || request.getText().trim().isEmpty()) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        } else {
            String sessionID = (String) request2.getAttribute(Constants.SESSION_ID);

            AppUser user = loginService.getUserFromSession(sessionID);
            if (user == null) {
                System.out.println(" ---- add MemberPackageProgressEntity : Possible attempt to hack in. ---- ");
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                long id = request.getPackage_mem_entity_id();
                CustomerPackageEntity obj = memberService.getCustomerPackageEntityFromId(id);
                //Member mem = memberService.getMemberFromId(id);
                if (obj == null || !obj.isEnabled()) {
                    System.out.print("The object with id doesnot exist or is disabled. Possible attempt to hack in.");
                    response.setCode(5);
                    response.setMessage(DATA_NOT_PRESENT);
                } else {
                    MemberPackageProgressEntity o1 = new MemberPackageProgressEntity();
                    o1.setText(request.getText().trim());
                    o1.setEntry_date(new Date());

                    o1.setCreated_on(new Date());
                    o1.setUpdated_on(new Date());
                    o1.setUpdatedBy(user.getUsername());
                    o1.setUpdateText(obj);

                    obj.getProgressUnit().add(o1);
                    //obj.setUpdated_on(new Date());
                    //obj.setUpdatedBy(user.getUsername());

                    memberService.saveProgressText(o1);
                    //memberService.editCustomerPackageEntity(obj);

                    response.setCode(0);
                    response.setMessage(SUCCESS);

                }
            }


        }
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }

    //to be expired packages members list
    @RequestMapping(value = "/index/home/memberexpired/list/", method = RequestMethod.POST)
    public ResponseEntity<MemberResponse> listMemberExpiry(@RequestBody MemberExpiredRequest request) {
        System.out.println(" --- Inside list memberexpired call ---- ");
        MemberResponse response = new MemberResponse();
        if (request == null || request.getStep() <= 0 ) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            int step = request.getStep();
            int timeExpiry = 15;//days
            MemberPaginationObject obj = memberService.getMember(step,PAGE_COUNT_SEC,timeExpiry);
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

    //reading the package_member_entity details
    @RequestMapping(value = "/index/home/customerpackageentity/read/", method = RequestMethod.POST)
    public ResponseEntity<CustomerPackageEntityReadResponse> readCustomerPackageEntity(@RequestBody CustomerPackageEntityReadRequest request) {
        System.out.println(" --- Inside read customerpackageentity call ---- ");
        CustomerPackageEntityReadResponse response = new CustomerPackageEntityReadResponse();
        if (request == null || request.getId() <= 0 ) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            long id = request.getId();
            response = memberService.getCustomerPackageEntityReadResponse(id);

            if(response == null){
                response = new CustomerPackageEntityReadResponse();
                response.setCode(4);
                response.setMessage(REQUEST_DATA_ABSENT);
                System.out.println("Its a hack attempt. Inside read customerpackageentity call");
            }else{
                response.setCode(0);
                response.setMessage(SUCCESS);

            }
        }
        /*if (request == null || request.getStep() <= 0 ) {
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            int step = request.getStep();
            int timeExpiry = 15;//days
            MemberPaginationObject obj = memberService.getMember(step,PAGE_COUNT_SEC,timeExpiry);
            if (obj == null) {
                response.setCode(5);
                response.setMessage(DATA_NOT_PRESENT);
            } else {
                response.setCode(0);
                response.setCount((int) obj.getTotalCount());
                response.setMembers(DataConversion.convertMemberToMemberPayLoad(obj.getPackages()));
            }

        }*/
        return new ResponseEntity<CustomerPackageEntityReadResponse>(response, HttpStatus.OK);
    }
}
