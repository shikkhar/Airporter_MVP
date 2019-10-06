package com.example.airporter.data;

public class OffersReceived {
    private String orderId;
    private String productName;
    private String deliverFrom;
    private String deliverTo;
    private String deliverBefore;
    private String price;
    private String productImagePath;
    private String orderDateTime;

    public OffersReceived(String orderId, String productName, String deliverFrom, String deliverTo, String deliverBefore, String price, String productImagePath, String orderDateTime) {
        this.orderId = orderId;
        this.productName = productName;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
        this.deliverBefore = deliverBefore;
        this.price = price;
        this.productImagePath = productImagePath;
        this.orderDateTime = orderDateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDeliverFrom() {
        return deliverFrom;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public String getDeliverBefore() {
        return deliverBefore;
    }

    public String getPrice() {
        return price;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

}
