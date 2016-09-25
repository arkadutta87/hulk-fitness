package com.websystique.springmvc.middleware;

import com.websystique.springmvc.service.LoginService;
import com.websystique.springmvc.utils.Constants;
import com.websystique.springmvc.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.CookieStore;

/**
 * Created by arkadutta on 24/08/16.
 */
public class GenericInterceptor extends HandlerInterceptorAdapter {

    class HackResponse {
        private int code;
        private String message;
        private String redirectURL;

        public HackResponse() {

        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRedirectURL() {
            return redirectURL;
        }

        public void setRedirectURL(String redirectURL) {
            this.redirectURL = redirectURL;
        }
    }

    @Autowired
    LoginService loginService;  //Service which will do all data retrieval/manipulation work

    private static final Logger logger = LoggerFactory
            .getLogger(GenericInterceptor.class);

    private static final String COOKIE_SESSION = "epme_session";
    private static final String INDEX_URL = "/epme/index/";
    private static final String DASHBOARD_URL = "/epme/index/dashboard/";
    private static final String[] allPass = {"/index/login/", "/index/changePassword/"};//temp

    private boolean isallPass(String uri) {
        //boolean flag = false;

        for (String str : allPass) {
            if (uri.contains(str)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSessionActive(String sessionid) {
        //responsible for checking whether the session is active or not
        if (sessionid.trim().isEmpty()) {
            return false;
        } else {
            return loginService.isSessionActive(sessionid);

        }
        //return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println(" ---- I am inside the interceptor ----- ");
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + ":: Start Time=" + System.currentTimeMillis() + ",  request URI -- " + request.getRequestURI() + ", other data - "
                + request);

        //logic
        String uri = request.getRequestURI();

        if (isallPass(uri)) {
            return true;
        } else {

            Cookie[] cookies = request.getCookies();
            if(cookies == null){
                if (uri.equals(INDEX_URL)) {
                    return true;
                } else if (uri.equals(DASHBOARD_URL)) {
                    //redirect him to index url
                    response.sendRedirect(INDEX_URL);
                    //return false;
                } else {
                    //any other url send the {code : 11 , message : You are trying to hack in ,redirectURL : '/epme/index/'}
                    HackResponse res = new HackResponse();
                    res.setCode(11);
                    res.setMessage("You are trying to hack in. Better luck next time");
                    res.setRedirectURL(INDEX_URL);

                    String json = Util.convertToJSON(res);
                    response.setContentType("text/x-json;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().write(json);
                    //json.write(response.getWriter());
                    //return true;
                }
            }
            String sessionId = "";

            for (Cookie ck : cookies) {
                System.out.println("Name - " + ck.getName() + " : Value : " + ck.getValue());
                if (ck.getName().equals(COOKIE_SESSION)) {
                    sessionId = ck.getValue();
                }
            }

            System.out.println("Cookie value -- " + sessionId);
            boolean flag = isSessionActive(sessionId);
            if (!flag) {
                if (uri.equals(INDEX_URL)) {
                    return true;
                } else if (uri.equals(DASHBOARD_URL)) {
                    //redirect him to index url
                    response.sendRedirect(INDEX_URL);
                    //return false;
                } else {
                    //any other url send the {code : 11 , message : You are trying to hack in ,redirectURL : '/epme/index/'}
                    HackResponse res = new HackResponse();
                    res.setCode(11);
                    res.setMessage("You are trying to hack in. Better luck next time");
                    res.setRedirectURL(INDEX_URL);

                    String json = Util.convertToJSON(res);
                    response.setContentType("text/x-json;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().write(json);
                    //json.write(response.getWriter());
                    //return true;
                }
            } else {
                if (uri.equals(INDEX_URL)) {
                    //redirect to dashboard
                    response.sendRedirect(DASHBOARD_URL);
                } else if (uri.equals(DASHBOARD_URL)) {
                    //add a session attribute as part of request
                    request.setAttribute(Constants.SESSION_ID, sessionId);

                    return true;
                } else {
                    request.setAttribute(Constants.SESSION_ID, sessionId);
                    return true;
                }

            }


        }


        //request.setAttribute("startTime", startTime);
        //if returned false, we need to make sure 'response' is sent
        return false;
    }

}
