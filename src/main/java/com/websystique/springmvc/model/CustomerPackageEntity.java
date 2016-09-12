package com.websystique.springmvc.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`member_id` int(11) unsigned  NOT NULL,
`package_id` int(11) unsigned NOT NULL,
 `discount_percentage`double(3,2) DEFAULT '0.00',
`final_price` double(11,2) DEFAULT '0.00',
`review_rating_id` int(11) unsigned ,
`top_up_time` int(11) unsigned NOT NULL DEFAULT '0',
`package_enrollment_date` datetime NOT NULL DEFAULT '1870-01-01 00:00:01',
`package_expiry_date` datetime NOT NULL DEFAULT '1870-01-01 00:00:01',
`created_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_on` datetime NOT NULL DEFAULT '1970-01-01 00:00:01',
`updated_by` varchar(60) NOT NULL Default '',
PRIMARY KEY (`id`),
KEY  `index_1` (`member_id`,`package_id`),
UNIQUE(`member_id`,`package_id`,`package_enrollment_date`),
CONSTRAINT `review_ref_1` FOREIGN KEY (`review_rating_id`) REFERENCES `Review_Rating` (`id`),
CONSTRAINT `member_ref_1` FOREIGN KEY (`member_id`) REFERENCES `Member` (`id`),
CONSTRAINT `package_ref_1` FOREIGN KEY (`package_id`) REFERENCES `Package` (`id`),
 */

@Entity
@Table(name = "customer_package_entity")
public class CustomerPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "member_id")
    private long member_id;

    @Column(name = "package_id")
    private long package_id;

    @Column(name = "top_up_time")
    private int top_up_time;

    @Column(name = "discount_percentage")
    private double discount_percentage;

    @Column(name = "final_price")
    private double final_price;

    @Column(name = "package_enrollment_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date package_enrollment_date;

    @Column(name = "package_expiry_date", columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date package_expiry_date;

    @Column(name = "updated_on", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_on;

    @Column(name = "created_on", columnDefinition = "DATETIME", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_on;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_rating_id")
    private ReviewRating reviewRating;

    @OneToMany(mappedBy = "updateText", fetch = FetchType.LAZY)
    private Set<MemberPackageProgressEntity> progressUnit = new HashSet<MemberPackageProgressEntity>(0);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(long package_id) {
        this.package_id = package_id;
    }

    public int getTop_up_time() {
        return top_up_time;
    }

    public void setTop_up_time(int top_up_time) {
        this.top_up_time = top_up_time;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    public Date getPackage_enrollment_date() {
        return package_enrollment_date;
    }

    public void setPackage_enrollment_date(Date package_enrollment_date) {
        this.package_enrollment_date = package_enrollment_date;
    }

    public Date getPackage_expiry_date() {
        return package_expiry_date;
    }

    public void setPackage_expiry_date(Date package_expiry_date) {
        this.package_expiry_date = package_expiry_date;
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

    public ReviewRating getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(ReviewRating reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Set<MemberPackageProgressEntity> getProgressUnit() {
        return progressUnit;
    }

    public void setProgressUnit(Set<MemberPackageProgressEntity> progressUnit) {
        this.progressUnit = progressUnit;
    }

    @Override
    public String toString() {
        return "CustomerPackageEntity{" +
                "id=" + id +
                ", member_id=" + member_id +
                ", package_id=" + package_id +
                ", top_up_time=" + top_up_time +
                ", discount_percentage=" + discount_percentage +
                ", final_price=" + final_price +
                ", package_enrollment_date=" + package_enrollment_date +
                ", package_expiry_date=" + package_expiry_date +
                ", updated_on=" + updated_on +
                ", created_on=" + created_on +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
