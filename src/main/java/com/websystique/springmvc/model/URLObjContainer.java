package com.websystique.springmvc.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class URLObjContainer {

    private List<URLObj> values;
    private int code;

    public URLObjContainer() {

    }

    public List<URLObj> getValues() {
        return values;
    }

    public void setValues(List<URLObj> values) {
        this.values = values;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "URLObjContainer{" +
                "values=" + values +
                ", code=" + code +
                '}';
    }
}
