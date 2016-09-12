package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 01/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageRequest {

    private int step;
    private PackageFilter filters;

    public PackageRequest() {

    }

    //getters and setters

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public PackageFilter getFilters() {
        return filters;
    }

    public void setFilters(PackageFilter filters) {
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
