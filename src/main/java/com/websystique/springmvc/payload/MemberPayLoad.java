package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 06/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPayLoad {

    private long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private int expiration_time_left_in_days;
    private String expiration_date;//should be YYYY-MM_DD hh:mi:si

    public MemberPayLoad(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getExpiration_time_left_in_days() {
        return expiration_time_left_in_days;
    }

    public void setExpiration_time_left_in_days(int expiration_time_left_in_days) {
        this.expiration_time_left_in_days = expiration_time_left_in_days;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    @Override
    public String toString() {
        return "MemberPayLoad{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", expiration_time_left_in_days=" + expiration_time_left_in_days +
                ", expiration_date='" + expiration_date + '\'' +
                '}';
    }
}
