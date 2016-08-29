package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by arkadutta on 22/08/16.
 */
@RestController
public class LogoutController {

    @Autowired
    LoginService loginService;  //Service which will do all data retrieval/manipulation work

    private static final String REQUEST_DATA_ABSENT = "All fields in request not present";
    private static final String SESSION_ABSENT = "All fields in request not present";
    private static final String LOGOUT_SUCCESSFULL = "Logout Successfull";

    private static final String LOGIN_PAGE = "../../index/";

    @RequestMapping(value = "/index/logout/", method = RequestMethod.POST)
    public ResponseEntity<LogoutResponse> loginAppUser(@RequestBody LogoutRequest request) {
        LogoutResponse response = new LogoutResponse();

        if(request == null  || request.getToken() == null){
            response.setCode(4);
            response.setMessage(REQUEST_DATA_ABSENT);
        }else{
            //extract the session
            String sessionStr = request.getToken();

            Session aSess = loginService.getSession(sessionStr);
            if(aSess!= null){
                aSess.setLogged_out(new Date());
                aSess.setIs_enabled(false);

                //update the session in db
                loginService.updateSession(aSess);

                //return the response
                response.setCode(0);
                response.setMessage(LOGOUT_SUCCESSFULL);
                response.setRedirectUrl(LOGIN_PAGE);

            }else{
                response.setCode(1);
                response.setMessage(SESSION_ABSENT);
                //response.setRedirectUrl(LOGIN_PAGE);
            }

        }

        return new ResponseEntity<LogoutResponse>(response, HttpStatus.OK);

    }
}
