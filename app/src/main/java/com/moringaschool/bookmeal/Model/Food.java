package com.moringaschool.bookmeal.Model;

import java.io.Serializable;

//serializable interface is for sending book object to another activity
public class Food implements Serializable {
    private String name,description,imgUrl;
    private double price;
    //for testing
    private int drawableResource;

    public Food() {
    }
    public Food(String name, String description, String imgUrl, double price, int drawableResource) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.drawableResource = drawableResource;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }
}
