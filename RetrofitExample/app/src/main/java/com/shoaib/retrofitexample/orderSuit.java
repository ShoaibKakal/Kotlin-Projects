package com.shoaib.retrofitexample;

import com.google.gson.annotations.SerializedName;

public class orderSuit {

    @SerializedName("customerId")
    private int customerId;

    @SerializedName("customerName")
    private String customerName;

    @SerializedName("deliveryDate")
    private String deliveryDate;

    @SerializedName("orderSuitName")
    private String orderSuitName;

    @SerializedName("orderSuitPrice")
    private String orderSuitPrice;

    @SerializedName("itemNumber")
    private String itemNumber;

    @SerializedName("orderSuitStatus")
    private String orderSuitStatus;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderSuitName() {
        return orderSuitName;
    }

    public void setOrderSuitName(String orderSuitName) {
        this.orderSuitName = orderSuitName;
    }

    public String getOrderSuitPrice() {
        return orderSuitPrice;
    }

    public void setOrderSuitPrice(String orderSuitPrice) {
        this.orderSuitPrice = orderSuitPrice;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getOrderSuitStatus() {
        return orderSuitStatus;
    }

    public void setOrderSuitStatus(String orderSuitStatus) {
        this.orderSuitStatus = orderSuitStatus;
    }
}
