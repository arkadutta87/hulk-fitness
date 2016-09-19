package com.websystique.springmvc.model;

/*
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`cus_pkg_ent_id` int(11) unsigned  NOT NULL,
`text` varchar(100) NOT NULL Default '',
`entry_date` datetime NOT NULL DEFAULT '1870-01-01 00:00:01',
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_by` varchar(60) NOT NULL Default '',
 */

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member_package_progress_entity")
public class MemberPackageProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name="text")
    private String text;

    @Column(name = "entry_date", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entry_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cus_pkg_ent_id", nullable=false)
    private CustomerPackageEntity updateText;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @Column(name = "updated_by")
    private String updatedBy;


    public MemberPackageProgressEntity(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public CustomerPackageEntity getUpdateText() {
        return updateText;
    }

    public void setUpdateText(CustomerPackageEntity updateText) {
        this.updateText = updateText;
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

    @Override
    public String toString() {
        return "MemberPackageProgressEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", entry_date=" + entry_date +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
