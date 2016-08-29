package com.websystique.springmvc.utils;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserPayLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadutta on 15/08/16.
 */
public class DataConversion {

    public static void copyUserToPayLoad(User aUser , UserPayLoad pLoad){
        pLoad.setId(aUser.getId());
        pLoad.setUsername(aUser.getUsername());
        pLoad.setAddress(aUser.getAddress());
        pLoad.setEmail(aUser.getEmail());
    }

    public static void copyPayLoadToUser(UserPayLoad aUser , User pLoad){
        pLoad.setId(aUser.getId());
        pLoad.setUsername(aUser.getUsername());
        pLoad.setAddress(aUser.getAddress());
        pLoad.setEmail(aUser.getUsername());
    }

    public static List<UserPayLoad> convertUserToPayload(List<User> users){

        if(users== null)
            return null;
        else{
            List<UserPayLoad> payLoad = new ArrayList<UserPayLoad>();
            for(User usr : users){
                UserPayLoad pLoad = new UserPayLoad();
                copyUserToPayLoad(usr,pLoad);
                payLoad.add(pLoad);
            }

            return payLoad;
        }
    }
}
