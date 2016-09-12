package com.websystique.springmvc.model;

import javax.persistence.*;
import java.util.Date;

/**
 CREATE TABLE `Review_Rating` (
 `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 `stars` int(1) NOT NULL Default '0',
 `review_text` varchar(110) NOT NULL Default '',
 `created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
 `updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
 `updated_by` varchar(60) NOT NULL Default '',
 PRIMARY KEY (`id`)
 )ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Entity
@Table(name = "Review_Rating")
public class ReviewRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "stars")
    private int stars;

    @Column(name = "review_text")
    private String review_text;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @Column(name="updated_by")
    private String updatedBy;

    @OneToOne(mappedBy="reviewRating",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private CustomerPackageEntity entity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
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

    public CustomerPackageEntity getEntity() {
        return entity;
    }

    public void setEntity(CustomerPackageEntity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "ReviewRating{" +
                "id=" + id +
                ", stars=" + stars +
                ", review_text='" + review_text + '\'' +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
