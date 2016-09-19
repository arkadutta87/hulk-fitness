package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by arkadutta on 16/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPackageEntityListResponse {

    private int code;
    private String message;

    private int count;
    private List<CustomerPackageEntityPayLoad> packages;

    public CustomerPackageEntityListResponse(){

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CustomerPackageEntityPayLoad> getPackages() {
        return packages;
    }

    public void setPackages(List<CustomerPackageEntityPayLoad> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "CustomerPackageEntityListResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", count=" + count +
                ", packages=" + packages +
                '}';
    }
}
