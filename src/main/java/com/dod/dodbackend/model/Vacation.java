package com.dod.dodbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vacation")
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String vacationPackage;
    private String ratings;
    private String details;
    private String discountPrice;
    private String originalPrice;
    private String discountPercentage;
    private String imageUrl;
    private String dealUrl;

    @Column(length = 1200)
    private String fromAndTo;

    public Vacation() {
    }

    public Vacation(Long id, String name, String vacationPackage, String ratings, String details, String discountPrice,
            String originalPrice, String discountPercentage, String imageUrl, String dealUrl, String fromAndTo) {
        this.id = id;
        this.name = name;
        this.vacationPackage = vacationPackage;
        this.ratings = ratings;
        this.details = details;
        this.discountPrice = discountPrice;
        this.originalPrice = originalPrice;
        this.discountPercentage = discountPercentage;
        this.imageUrl = imageUrl;
        this.dealUrl = dealUrl;
        this.fromAndTo = fromAndTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVacationPackage() {
        return vacationPackage;
    }

    public void setVacationPackage(String vacationPackage) {
        this.vacationPackage = vacationPackage;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDealUrl() {
        return dealUrl;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public String getFromAndTo() {
        return fromAndTo;
    }

    public void setFromAndTo(String fromAndTo) {
        this.fromAndTo = fromAndTo;
    }

    

    

    
}
