package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.websystique.springmvc.model.Member;

import java.util.List;

/**
 * Created by arkadutta on 16/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPackageEntityPaginationObject {

    private long totalCount;
    private List<CustomerPackageEntityPayLoad> aList;

    public CustomerPackageEntityPaginationObject(){

    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<CustomerPackageEntityPayLoad> getaList() {
        return aList;
    }

    public void setaList(List<CustomerPackageEntityPayLoad> aList) {
        this.aList = aList;
    }

    @Override
    public String toString() {
        return "CustomerPackageEntityPaginationObject{" +
                "totalCount=" + totalCount +
                ", aList=" + aList +
                '}';
    }
}
