package com.moringaschool.bookmeal.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MenuResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("menu_image")
    @Expose
    private String menuImage;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("owner")
    @Expose
    private String owner;

    /**
     * No args constructor for use in serialization
     *
     */
    public MenuResponse() {
    }

    /**
     *
     * @param owner
     * @param price
     * @param created
     * @param name
     * @param description
     * @param menuImage
     * @param id
     */
    public MenuResponse(String id, String name, String description, Integer price, String menuImage, String created, String owner) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.menuImage = menuImage;
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
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
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