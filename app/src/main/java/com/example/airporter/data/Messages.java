package com.example.airporter.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Messages implements Parcelable {
    private  String orderId;
    private String bidderId;
    private String shopperId;
    private String bidderName;
    private String shopperName;

    public Messages(String orderId, String bidderId, String shopperId, String bidderName, String shopperName) {
        this.orderId = orderId;
        this.bidderId = bidderId;
        this.shopperId = shopperId;
        this.bidderName = bidderName;
        this.shopperName = shopperName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public String getShopperId() {
        return shopperId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public String getShopperName() {
        return shopperName;
    }

    protected Messages(Parcel in) {
        orderId = in.readString();
        bidderId = in.readString();
        shopperId = in.readString();
        bidderName = in.readString();
        shopperName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(bidderId);
        dest.writeString(shopperId);
        dest.writeString(bidderName);
        dest.writeString(shopperName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Messages> CREATOR = new Parcelable.Creator<Messages>() {
        @Override
        public Messages createFromParcel(Parcel in) {
            return new Messages(in);
        }

        @Override
        public Messages[] newArray(int size) {
            return new Messages[size];
        }
    };
}
