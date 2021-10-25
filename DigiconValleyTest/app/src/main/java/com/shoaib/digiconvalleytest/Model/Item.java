package com.shoaib.digiconvalleytest.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Item {


    @SerializedName("orderNo")
    private String orderNo;

    @SerializedName("orderStatus")
    private String orderStatus;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("orderDeliveryDate")
    private String orderDeliveryDate;

    @SerializedName("orderReceivedAmounts")
    private ArrayList<OrderReceivedAmounts> orderReceivedAmounts;

    @SerializedName("orderSuit")
    private ArrayList<orderSuit> orderSuit;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public ArrayList<OrderReceivedAmounts> getOrderReceivedAmounts() {
        return orderReceivedAmounts;
    }

    public void setOrderReceivedAmounts(ArrayList<OrderReceivedAmounts> orderReceivedAmounts) {
        this.orderReceivedAmounts = orderReceivedAmounts;
    }

    public ArrayList<com.shoaib.digiconvalleytest.Model.orderSuit> getOrderSuit() {
        return orderSuit;
    }

    public void setOrderSuit(ArrayList<com.shoaib.digiconvalleytest.Model.orderSuit> orderSuit) {
        this.orderSuit = orderSuit;
    }
}
