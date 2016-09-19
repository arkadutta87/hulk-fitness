package com.websystique.springmvc.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by arkadutta on 13/09/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberCRUDResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String alternateMobile;
    private String date_of_birth;
    private String enrollment_date;
    private String latest_pkg_expiry_date;
    private String profile_pic_url;

    //Address part
    private long address_id;
    private String lineOne;
    private String lineTwo;
    private int pinCode;
    private String city;
    private String state;
    private String country;

    private int pkg_utilization_percentage;

    private int code;
    private String message;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(String enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getLatest_pkg_expiry_date() {
        return latest_pkg_expiry_date;
    }

    public void setLatest_pkg_expiry_date(String latest_pkg_expiry_date) {
        this.latest_pkg_expiry_date = latest_pkg_expiry_date;
    }

    public int getPkg_utilization_percentage() {
        return pkg_utilization_percentage;
    }

    public void setPkg_utilization_percentage(int pkg_utilization_percentage) {
        this.pkg_utilization_percentage = pkg_utilization_percentage;
    }

    @Override
    public String toString() {
        return "MemberCRUDResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", alternateMobile='" + alternateMobile + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", enrollment_date='" + enrollment_date + '\'' +
                ", latest_pkg_expiry_date='" + latest_pkg_expiry_date + '\'' +
                ", profile_pic_url='" + profile_pic_url + '\'' +
                ", address_id=" + address_id +
                ", lineOne='" + lineOne + '\'' +
                ", lineTwo='" + lineTwo + '\'' +
                ", pinCode=" + pinCode +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pkg_utilization_percentage=" + pkg_utilization_percentage +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
