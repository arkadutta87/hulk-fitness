package com.websystique.springmvc.common;

/**
 * Created by arkadutta on 17/08/16.
 */
public enum InputEnum {

    USERNAME("username") ,
    FIRST_NAME("firstname"),
    LAST_NAME("lastname"),
    MOBILE("mobile"),
    PASSWORD("password"),
    RETYPE_PASSWORD("retypepassword"),
    NULL("NULL");

    public final String value;

    private InputEnum(String value){
        this.value = value;
    }


}
