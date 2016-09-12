package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by arkadutta on 06/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberResponse {

    private int code;
    private List<MemberPayLoad> members;
    private String message;
    private int count;

    public MemberResponse() {

    }

    //getters and setters
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

    public List<MemberPayLoad> getMembers() {
        return members;
    }

    public void setMembers(List<MemberPayLoad> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "PackageResponse{" +
                "code=" + code +
                ", membets=" + members +
                ", message='" + message + '\'' +
                ", count=" + count +
                '}';
    }
}
