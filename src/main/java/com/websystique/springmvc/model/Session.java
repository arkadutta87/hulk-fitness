package com.websystique.springmvc.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/*
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
        `session_id` varchar(100) NOT NULL Default ‘’,
        `logged_in` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
        `logged_out` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
        `is_enabled` tinyint(1) NOT NULL default ‘1’,
        `app_user_id` int(11) unsigned NOT NULL
*/
@Entity
@Table(name = "Session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name="session_id")
    private String session_id;

    @Column(name = "logged_in", columnDefinition = "DATETIME",updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logged_in;

    @Column(name = "logged_out", columnDefinition = "DATETIME" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date logged_out;

    @Column(name = "is_enabled", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean is_enabled;

    @ManyToOne
    @JoinColumn(name="app_user_id", nullable=false)
    private AppUser user;

    //Hibernate requires no-args constructor
    public Session(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Date getLogged_in() {
        return logged_in;
    }

    public void setLogged_in(Date logged_in) {
        this.logged_in = logged_in;
    }

    public Date getLogged_out() {
        return logged_out;
    }

    public void setLogged_out(Date logged_out) {
        this.logged_out = logged_out;
    }

    public boolean is_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
