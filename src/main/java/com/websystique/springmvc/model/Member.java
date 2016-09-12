package com.websystique.springmvc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by arkadutta on 05/09/16.
 */
 /*
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 `first_name` varchar(40) NOT NULL Default '',
 `last_name` varchar(40) NOT NULL  default '',
`email` varchar(40) NOT NULL default '',
`mobile` varchar(15) NOT NULL default '',
`alternate_mobile` varchar(15) NOT NULL default '',
`address_id` int(11) unsigned ,
`profile_pic_url` varchar(80) NOT NULL default '',
`date_of_birth` datetime NOT NULL DEFAULT '1870-01-01 00:00:01',
`enrollment_date` datetime NOT NULL DEFAULT '1870-01-01 00:00:01',
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_by` varchar(60) NOT NULL Default '',
     */

@Entity
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "alternate_mobile")
    private String alternateMobile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "profile_pic_url")
    private String profile_pic_url;

    @Column(name = "date_of_birth", columnDefinition = "DATETIME" ,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_of_birth;

    @Column(name = "enrollment_date", columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollment_date;

    @Column(name = "latest_pkg_expiry", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date latest_pkg_expiry;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @Column(name = "updated_by")
    private String updatedBy;


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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Date getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(Date enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getLatest_pkg_expiry() {
        return latest_pkg_expiry;
    }

    public void setLatest_pkg_expiry(Date latest_pkg_expiry) {
        this.latest_pkg_expiry = latest_pkg_expiry;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", alternateMobile='" + alternateMobile + '\'' +
                ", profile_pic_url='" + profile_pic_url + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", enrollment_date=" + enrollment_date +
                ", latest_pkg_expiry=" + latest_pkg_expiry +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}

