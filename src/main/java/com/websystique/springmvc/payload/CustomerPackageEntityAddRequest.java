package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 18/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPackageEntityAddRequest {

    private long package_id;
    private long member_id;

    private double discount_percentage;
    private int top_up_time;
    private String package_enrollment_date;//MM/dd/yyyy

    public long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(long package_id) {
        this.package_id = package_id;
    }

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public int getTop_up_time() {
        return top_up_time;
    }

    public void setTop_up_time(int top_up_time) {
        this.top_up_time = top_up_time;
    }

    public String getPackage_enrollment_date() {
        return package_enrollment_date;
    }

    public void setPackage_enrollment_date(String package_enrollment_date) {
        this.package_enrollment_date = package_enrollment_date;
    }

    @Override
    public String toString() {
        return "CustomerPackageEntityAddRequest{" +
                "package_id=" + package_id +
                ", member_id=" + member_id +
                ", discount_percentage=" + discount_percentage +
                ", top_up_time=" + top_up_time +
                ", package_enrollment_date='" + package_enrollment_date + '\'' +
                '}';
    }
}
