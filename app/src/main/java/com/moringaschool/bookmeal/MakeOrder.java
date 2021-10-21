package com.moringaschool.bookmeal;

public class MakeOrder {
    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public MakeOrder() {
    }

    public MakeOrder(String menu_id, Integer quantity) {
        this.menu_id = menu_id;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    String menu_id;
    Integer quantity;

}
