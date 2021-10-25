package com.shoaib.digiconvalleytest.Model;

import com.google.gson.annotations.SerializedName;

public class OrderReceivedAmounts {

    @SerializedName("receivedAmountId")
    private int receivedAmountId;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("amount")
    private int amount;


    @SerializedName("date")
    private String date;


    public int getReceivedAmountId() {
        return receivedAmountId;
    }

    public void setReceivedAmountId(int receivedAmountId) {
        this.receivedAmountId = receivedAmountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
