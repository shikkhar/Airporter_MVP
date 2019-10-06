package com.example.airporter.data;

import android.os.Parcel;
import android.os.Parcelable;

public class OffersSent implements Parcelable {
    private  String orderId;
    private  String offerId;
    private String shopperName;
    private String productName;
    private String deliverFrom;
    private String deliverTo;
    private String deliverBefore;
    private String price;
    private  String orderImage;
    private  String orderDateTime;
    private  String offerPrice;
    private String offerAccepted;
    private  String offerRejected;
    private  String offerCancelled;
    private String orderInactive;
    private String reward;
    private String shopperImagePath;

    public OffersSent(String orderId, String offerId, String shopperName, String productName, String deliverFrom, String deliverTo, String deliverBefore, String price, String orderImage, String orderDateTime, String offerPrice, String offerAccepted, String offerRejected, String offerCancelled, String orderInactive, String reward, String shopperImagePath) {
        this.orderId = orderId;
        this.offerId = offerId;
        this.shopperName = shopperName;
        this.productName = productName;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
        this.deliverBefore = deliverBefore;
        this.price = price;
        this.orderImage = orderImage;
        this.orderDateTime = orderDateTime;
        this.offerPrice = offerPrice;
        this.offerAccepted = offerAccepted;
        this.offerRejected = offerRejected;
        this.offerCancelled = offerCancelled;
        this.orderInactive = orderInactive;
        this.reward = reward;
        this.shopperImagePath = shopperImagePath;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getShopperName() {
        return shopperName;
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

    public String getOrderImage() {
        return orderImage;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public String getOfferAccepted() {
        return offerAccepted;
    }

    public String getOfferRejected() {
        return offerRejected;
    }

    public String getOfferCancelled() {
        return offerCancelled;
    }

    public String getOrderInactive() {
        return orderInactive;
    }

    public String getReward() {
        return reward;
    }

    public String getShopperImagePath() {
        return shopperImagePath;
    }


    protected OffersSent(Parcel in) {
        orderId = in.readString();
        offerId = in.readString();
        shopperName = in.readString();
        productName = in.readString();
        deliverFrom = in.readString();
        deliverTo = in.readString();
        deliverBefore = in.readString();
        price = in.readString();
        orderImage = in.readString();
        orderDateTime = in.readString();
        offerPrice = in.readString();
        offerAccepted = in.readString();
        offerRejected = in.readString();
        offerCancelled = in.readString();
        orderInactive = in.readString();
        reward = in.readString();
        shopperImagePath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(offerId);
        dest.writeString(shopperName);
        dest.writeString(productName);
        dest.writeString(deliverFrom);
        dest.writeString(deliverTo);
        dest.writeString(deliverBefore);
        dest.writeString(price);
        dest.writeString(orderImage);
        dest.writeString(orderDateTime);
        dest.writeString(offerPrice);
        dest.writeString(offerAccepted);
        dest.writeString(offerRejected);
        dest.writeString(offerCancelled);
        dest.writeString(orderInactive);
        dest.writeString(reward);
        dest.writeString(shopperImagePath);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OffersSent> CREATOR = new Parcelable.Creator<OffersSent>() {
        @Override
        public OffersSent createFromParcel(Parcel in) {
            return new OffersSent(in);
        }

        @Override
        public OffersSent[] newArray(int size) {
            return new OffersSent[size];
        }
    };

}
