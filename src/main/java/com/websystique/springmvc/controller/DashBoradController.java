package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.AppUser;
import com.websystique.springmvc.service.LoginService;
import com.websystique.springmvc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by arkadutta on 23/08/16.
 */

@Controller
public class DashBoradController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/index/dashboard/" , method = RequestMethod.GET)
    public String getIndexPage(Model model, HttpServletRequest request) {
        //return "UserManagement";
        String sessionID = (String)request.getAttribute(Constants.SESSION_ID);

        AppUser user = loginService.getUserFromSession(sessionID);

        if(user!= null){
            String firstName = user.getFirstname().trim();
            String lastName = user.getLastname().trim();

            model.addAttribute(Constants.USER_NAME,firstName + " " + lastName);
        }



        return "DashBoardPage";
    }
}
