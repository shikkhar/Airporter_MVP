package com.example.airporter.data;

public class Order {

    private String orderId;
    private String shopperName;
    private String productName;
    private String deliverFrom;
    private String deliverTo;
    private String deliverBefore;
    private String price;
    private String productImagePath;
    private String orderDateTime;
    private String shopperImagePath;
    private String reward;



    public Order(String orderId, String shopperName, String productName, String deliverFrom, String deliverTo, String deliverBefore, String price, String orderImage, String orderDateTime, String shopperImagePath) {
        this.orderId = orderId;
        this.shopperName = shopperName;
        this.productName = productName;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
        this.deliverBefore = deliverBefore;
        this.price = price;
        this.productImagePath  = orderImage;
        this.orderDateTime = orderDateTime;
        this.shopperImagePath = shopperImagePath;

    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getShopperImagePath() {
        return shopperImagePath;
    }

    public void setShopperImagePath(String shopperImagePath) {
        this.shopperImagePath = shopperImagePath;
    }

//    public int getImageResourceId(Context context)
//    {
//        return context.getResources().getIdentifier(this.orderImage, "drawable", context.getPackageName());
//    }

    public String getShopperName() {
        return shopperName;
    }

    public void setShopperName(String shopperName) {
        this.shopperName = shopperName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeliverFrom() {
        return deliverFrom;
    }

    public void setDeliverFrom(String deliverFrom) {
        this.deliverFrom = deliverFrom;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }

    public String getDeliverBefore() {
        return deliverBefore;
    }

    public void setDeliverBefore(String deliverBefore) {
        this.deliverBefore = deliverBefore;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductImagePath () {
        return productImagePath ;
    }

    public void setProductImagePath (String productImagePath ) {
        this.productImagePath  = productImagePath ;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

}
