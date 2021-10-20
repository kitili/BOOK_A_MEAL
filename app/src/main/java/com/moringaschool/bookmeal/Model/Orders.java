package com.moringaschool.bookmeal.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moringaschool.bookmeal.Data;

public class Orders {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer")
    @Expose
    private Data customer;
    @SerializedName("orders")
    @Expose
    private List<Object> orders = null;
    @SerializedName("is_open")
    @Expose
    private Boolean isOpen;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("created")
    @Expose
    private String created;

    /**
     * No args constructor for use in serialization
     *
     */
    public Orders() {
    }


    public Orders(String id, Data customer, List<Object> orders, Boolean isOpen, Integer totalAmount, String created) {
        super();
        this.id = id;
        this.customer = customer;
        this.orders = orders;
        this.isOpen = isOpen;
        this.totalAmount = totalAmount;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Data getCustomer() {
        return customer;
    }

    public void setCustomer(Data customer) {
        this.customer = customer;
    }

    public List<Object> getOrders() {
        return orders;
    }

    public void setOrders(List<Object> orders) {
        this.orders = orders;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}