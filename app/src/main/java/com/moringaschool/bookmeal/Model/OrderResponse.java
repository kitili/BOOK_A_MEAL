package com.moringaschool.bookmeal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer")
    @Expose
    private Data customer;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
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
    public OrderResponse() {
    }

    /**
     *
     * @param totalAmount
     * @param isOpen
     * @param created
     * @param orders
     * @param id
     * @param customer
     */
    public OrderResponse(String id, Data customer, List<Order> orders, Boolean isOpen, Integer totalAmount, String created) {
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
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
