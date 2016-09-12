package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 02/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageCRUDResponse {

    private long id;
    private String name;
    private String specialName;
    private String durationUnit;
    private double durationValue;
    private double price;
    private String packageDetails;
    private boolean isTrial;

    private int code;
    private String message;

    public PackageCRUDResponse(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public double getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(double durationValue) {
        this.durationValue = durationValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
    }

    public boolean isTrial() {
        return isTrial;
    }

    public void setTrial(boolean trial) {
        isTrial = trial;
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
        return "PackageCRUDResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialName='" + specialName + '\'' +
                ", durationUnit='" + durationUnit + '\'' +
                ", durationValue=" + durationValue +
                ", price=" + price +
                ", packageDetails='" + packageDetails + '\'' +
                ", isTrial=" + isTrial +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
