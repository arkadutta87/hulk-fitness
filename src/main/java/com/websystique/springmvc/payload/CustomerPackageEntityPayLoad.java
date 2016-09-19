package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 16/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPackageEntityPayLoad {

    private long id;
    private String pkg_name;
    private double final_price;
    private String enrollment_date;
    private int pkg_utilization_percentage;
    private String package_expiry_date;

    public CustomerPackageEntityPayLoad(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPkg_name() {
        return pkg_name;
    }

    public void setPkg_name(String pkg_name) {
        this.pkg_name = pkg_name;
    }

    public double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    public String getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(String enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public int getPkg_utilization_percentage() {
        return pkg_utilization_percentage;
    }

    public void setPkg_utilization_percentage(int pkg_utilization_percentage) {
        this.pkg_utilization_percentage = pkg_utilization_percentage;
    }

    public String getPackage_expiry_date() {
        return package_expiry_date;
    }

    public void setPackage_expiry_date(String package_expiry_date) {
        this.package_expiry_date = package_expiry_date;
    }

    @Override
    public String toString() {
        return "CustomerPackageEntityPayLoad{" +
                "id=" + id +
                ", pkg_name='" + pkg_name + '\'' +
                ", final_price=" + final_price +
                ", enrollment_date='" + enrollment_date + '\'' +
                ", pkg_utilization_percentage=" + pkg_utilization_percentage +
                ", package_expiry_date='" + package_expiry_date + '\'' +
                '}';
    }
}
