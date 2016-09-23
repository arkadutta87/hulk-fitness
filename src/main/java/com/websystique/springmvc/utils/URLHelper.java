package com.websystique.springmvc.utils;

import java.util.ArrayList;
import java.util.List;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.websystique.springmvc.common.ConstantContainer;
import com.websystique.springmvc.model.URLObj;
import com.websystique.springmvc.model.URLObjContainer;

public class URLHelper {
	
	private static String[] urls = {"../../static/html/firstPage.html",
			"../../static/html/a.html","../../static/html/members.html","../../static/html/b.html","../../static/html/addPackage.html"
            ,"../../static/html/changePassword.html","../../static/html/addMember.html","../../static/html/c.html",
            "../../static/html/profile-part.html","../../static/html/package-part.html","../../static/html/package-association.html",
            "../../static/html/editMember.html","../../static/html/package_association_details.html",
            "../../static/html/subscription-ending.html"};
	
	private static URLHelper ourInstance = new URLHelper();
	private URLObjContainer container ;

    public static URLHelper getInstance() {
        return ourInstance;
    }

    private URLHelper(){
        container = new URLObjContainer();
        container.setCode(0);
        
        List<URLObj> aList = new ArrayList<URLObj>();
        int i = 1;
        for(String str : urls){
        	URLObj aObj = new URLObj();
        	aObj.setId(i);
        	aObj.setUrl(str);
        	aList.add(aObj);
        	++i;
        }
        
        container.setValues(aList);
        
    }
    
    public URLObjContainer getURLData(){
    	return this.container;
    }

}
