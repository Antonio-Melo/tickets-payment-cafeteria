package com.antoniomelo.cafeteria;

public class ListOrder {

    private String id;
    private Integer orderNumber;
    private String username;
    private String order;

    public ListOrder(String id, Integer orderNumber, String username, String order) {
        this.id = id;
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

    public String getId() {
        return id;
    }
}
