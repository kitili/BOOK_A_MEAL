package com.moringaschool.bookmeal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {
     private String id;

    private String name;

    private String description;

    private Integer price;

    private String menu_image;

    private String created;

    private String owner;


    public Menu() {
    }

    public Menu(String id, String name, String description, Integer price, String menu_image, String created, String owner) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.menu_image = menu_image;
        this.created = created;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMenuImage() {
        return menu_image;
    }

    public void setMenuImage(String menuImage) {
        this.menu_image = menuImage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
