package com.example.airporter.data;

public class OffersReceivedDetails {
    private String orderId;
    private String offerId;
    private String bidderId;
    private String bidderName;
    private String price;
    private String bidderImage;
    private String offerPrice;
    private String reward;

    public OffersReceivedDetails(String orderId, String offerId, String bidderId, String bidderName, String price, String bidderImage, String offerPrice, String reward) {
        this.orderId = orderId;
        this.offerId = offerId;
        this.bidderId = bidderId;
        this.bidderName = bidderName;
        this.price = price;
        this.bidderImage = bidderImage;
        this.offerPrice = offerPrice;
        this.reward = reward;
    }

    public String getReward() {
        return reward;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public String getPrice() {
        return price;
    }

    public String getBidderImage() {
        return bidderImage;
    }

    public String getOfferPrice() {
        return offerPrice;
    }
}
