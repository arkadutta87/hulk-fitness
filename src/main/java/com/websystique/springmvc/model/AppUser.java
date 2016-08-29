package com.websystique.springmvc.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/*
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 `username` varchar(100) NOT NULL Default '',
 `password` varchar(30) NOT NULL ,
`firstname` varchar(50) NOT NULL default '',
`lastname` varchar(100) NOT NULL default '',
`isadmin` tinyint(1) NOT NULL default '0',
`isotp` tinyint(1) NOT NULL default '1',
`mobile` int(15) default NULL,
  `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
  `updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01'
     */

@Entity
@Table(name = "AppUser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "isadmin", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isadmin;

    @Column(name = "isactive", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isactive;

    @Column(name = "isotp", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isotp;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Session> sessions  = new HashSet<Session>(0);;

    public AppUser() {

    }

    public boolean isactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public boolean isotp() {
        return isotp;
    }

    public void setIsotp(boolean isotp) {
        this.isotp = isotp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isadmin=" + isadmin +
                ", isactive=" + isactive +
                ", isotp=" + isotp +
                ", mobile='" + mobile + '\'' +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", sessions=" + sessions +
                '}';
    }
}
