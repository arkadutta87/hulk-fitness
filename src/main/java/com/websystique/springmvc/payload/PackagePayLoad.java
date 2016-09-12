package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 01/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackagePayLoad {

    private long id;
    private String name;
    private String specialName ;
    private boolean isTrial;
    private String duration;//should be x months or x days
    private String price;//should be ₹ 10,000.
    private String updatedOn ; // should be YYYYY-MM-DDThh:mi:si

    public PackagePayLoad(){

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

    public boolean isTrial() {
        return isTrial;
    }

    public void setTrial(boolean trial) {
        isTrial = trial;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "PackagePayLoad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialName='" + specialName + '\'' +
                ", isTrial=" + isTrial +
                ", duration='" + duration + '\'' +
                ", price='" + price + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                '}';
    }
}
