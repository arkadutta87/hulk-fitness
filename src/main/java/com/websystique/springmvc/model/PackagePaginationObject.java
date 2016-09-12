package com.websystique.springmvc.model;

import java.util.List;

/**
 * Created by arkadutta on 01/09/16.
 */
public class PackagePaginationObject {

    private long totalCount;
    private List<Package> packages;

    public PackagePaginationObject(){

    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "PackagePaginationObject{" +
                "totalCount=" + totalCount +
                ", packages=" + packages +
                '}';
    }
}
