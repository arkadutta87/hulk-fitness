package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 06/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberFilter {

    private String nameStr;
    private String mobileStr;

    public MemberFilter() {

    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public String getMobileStr() {
        return mobileStr;
    }

    public void setMobileStr(String mobileStr) {
        this.mobileStr = mobileStr;
    }

    @Override
    public String toString() {
        return "MemberFilter{" +
                "nameStr='" + nameStr + '\'' +
                ", mobileStr='" + mobileStr + '\'' +
                '}';
    }
}
