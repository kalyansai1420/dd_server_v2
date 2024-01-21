package com.dod.dodbackend.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "product")
public class Product {
    @Id
    private String sku;
    private String name;
    @Column(length = 2500,columnDefinition="TEXT")
    private String description;
    private String campaign;
    private String category;
    private String subCategory;
    private String regularPrice;
    private String salePrice;
    private String discountPercentage;
    private String image;
    private String rating;
	@Column(columnDefinition="TEXT")
    private String url;
    
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("product")
    private List<ProductImage> images;


    public Product() {
    }


   

    public Product(String sku, String name, String description, String campaign, String category, String subCategory,
            String regularPrice, String salePrice, String discountPercentage, String image, String rating, String url,
            List<ProductImage> images) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.campaign = campaign;
        this.category = category;
        this.subCategory = subCategory;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.rating = rating;
        this.url = url;
        this.images = images;
    }




    public String getSku() {
        return sku;
    }


    public void setSku(String sku) {
        this.sku = sku;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getCampaign() {
        return campaign;
    }


    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public String getSubCategory() {
        return subCategory;
    }


    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }


    public String getRegularPrice() {
        return regularPrice;
    }


    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }


    public String getSalePrice() {
        return salePrice;
    }


    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }


    public String getDiscountPercentage() {
        return discountPercentage;
    }


    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }
    


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public List<ProductImage> getImages() {
        return images;
    }


    public void setImages(List<ProductImage> images) {
        this.images = images;
    }




    public String getRating() {
        return rating;
    }




    public void setRating(String rating) {
        this.rating = rating;
    }


    
    

    
    

}
