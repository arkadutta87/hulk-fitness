package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by arkadutta on 19/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPackageEntityReadResponse {

    private String package_name;
    private double discount_percentage;
    private double final_price;
    private int top_up_time;
    private String package_enrollment_date;
    private String package_expiry_date;
    private String package_details;

    private List<ProgressBlock>  progress;
    private int code;
    private String message;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
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

    public String getPackage_expiry_date() {
        return package_expiry_date;
    }

    public void setPackage_expiry_date(String package_expiry_date) {
        this.package_expiry_date = package_expiry_date;
    }

    public String getPackage_details() {
        return package_details;
    }

    public void setPackage_details(String package_details) {
        this.package_details = package_details;
    }

    public List<ProgressBlock> getProgress() {
        return progress;
    }

    public void setProgress(List<ProgressBlock> progress) {
        this.progress = progress;
    }

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

    @Override
    public String toString() {
        return "CustomerPackageEntityReadResponse{" +
                "package_name='" + package_name + '\'' +
                ", discount_percentage=" + discount_percentage +
                ", final_price=" + final_price +
                ", top_up_time=" + top_up_time +
                ", package_enrollment_date='" + package_enrollment_date + '\'' +
                ", package_expiry_date='" + package_expiry_date + '\'' +
                ", package_details='" + package_details + '\'' +
                ", progress=" + progress +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
