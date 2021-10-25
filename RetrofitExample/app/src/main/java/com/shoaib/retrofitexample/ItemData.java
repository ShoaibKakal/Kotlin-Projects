package com.shoaib.retrofitexample;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemData {

    private String orderNo;
    private String orderDesc;
    private String orderDate;


    @SerializedName("orderSuit")
    private ArrayList<orderSuit> orderSuit;


    public ItemData(String orderNo, String orderDesc, String orderDate, ArrayList<orderSuit> orderSuit) {
        this.orderNo = orderNo;
        this.orderDesc = orderDesc;
        this.orderDate = orderDate;
        this.orderSuit = orderSuit;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public ArrayList<com.shoaib.retrofitexample.orderSuit> getOrderSuit() {
        return orderSuit;
    }

    public void setOrderSuit(ArrayList<com.shoaib.retrofitexample.orderSuit> orderSuit) {
        this.orderSuit = orderSuit;
    }
}

