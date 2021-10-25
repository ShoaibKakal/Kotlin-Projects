package com.shoaib.digiconvalleytest.Model;

public class Order {
    private int imageId;
    private String name;
    private int items;
    private int price;

    public Order(int imageId, String name, int price, int items) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.items = items;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
}
