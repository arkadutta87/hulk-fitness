package com.websystique.springmvc.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by arkadutta on 01/09/16.
 */
@Entity
@Table(name = "Package")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="special_name")
    private String specialName;

    @Column(name="duration_unit")
    private String durationUnit;

    @Column(name = "duration_value")
    private double durationValue;

    @Column(name = "price")
    private double price;

    @Column(name = "is_trial", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isTrial;

    @Column(name="package_details")
    private String packageDetails;

    @Column(name = "created_on", columnDefinition = "DATETIME",updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on", columnDefinition = "DATETIME" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy; //should be the username/email if the user

    @Column(name = "is_enabled", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isEnabled;

    //Hibernate requires no-args constructor
    public Package(){}

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

    public boolean isTrial() {
        return isTrial;
    }

    public void setTrial(boolean trial) {
        isTrial = trial;
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialName='" + specialName + '\'' +
                ", durationUnit='" + durationUnit + '\'' +
                ", durationValue=" + durationValue +
                ", price=" + price +
                ", isTrial=" + isTrial +
                ", packageDetails='" + packageDetails + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
