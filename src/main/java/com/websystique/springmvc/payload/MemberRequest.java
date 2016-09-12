package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 06/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberRequest {

    private int step;
    private MemberFilter filters;

    public MemberRequest() {

    }

    //getters and setters

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public MemberFilter getFilters() {
        return filters;
    }

    public void setFilters(MemberFilter filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "PackageRequest{" +
                "step=" + step +
                ", filters=" + filters +
                '}';
    }
}
