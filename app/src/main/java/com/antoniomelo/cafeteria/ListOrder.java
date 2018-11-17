package com.antoniomelo.cafeteria;

public class ListOrder {

    private Integer orderNumber;
    private String username;
    private String order;

    public ListOrder(Integer orderNumber, String username, String order) {
        this.orderNumber = orderNumber;
        this.username = username;
        this.order = order;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getOrder() {
        return order;
    }
}
