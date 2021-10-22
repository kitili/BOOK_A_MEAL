package com.moringaschool.bookmeal.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMenuRequest {
    private String name;
    private String description;
    private Integer price;

    public AddMenuRequest() {
    }

    public AddMenuRequest(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
