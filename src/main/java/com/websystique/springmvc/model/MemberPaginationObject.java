package com.websystique.springmvc.model;

import java.util.List;

/**
 * Created by arkadutta on 06/09/16.
 */
public class MemberPaginationObject {

    private long totalCount;
    private List<Member> packages;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Member> getPackages() {
        return packages;
    }

    public void setPackages(List<Member> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "MemberPaginationObject{" +
                "totalCount=" + totalCount +
                ", packages=" + packages +
                '}';
    }
}
