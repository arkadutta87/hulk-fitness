package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by arkadutta on 01/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageResponse {

    private int code;
    private List<PackagePayLoad> packages;
    private String message;
    private int count;

    public PackageResponse() {

    }

    //getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<PackagePayLoad> getPackages() {
        return packages;
    }

    public void setPackages(List<PackagePayLoad> packages) {
        this.packages = packages;
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

    @Override
    public String toString() {
        return "PackageResponse{" +
                "code=" + code +
                ", packages=" + packages +
                ", message='" + message + '\'' +
                ", count=" + count +
                '}';
    }
}
