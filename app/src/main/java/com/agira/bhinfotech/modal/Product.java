package com.agira.bhinfotech.modal;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String brand;
    private String image;

    private int count = 0;
    private int newPrice = 999;
    private int oldPrice = 1200;

    public String getPrice() {
        return "<del style=\"color:red;\"> &#8377;" + oldPrice + "</del> <strong> &#8377; <ins>" + newPrice + "</ins>!</strong>";
    }

    public String getAllPrice() {

        int total = count * newPrice;
        return String.format("\u20B9 %s", total);
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getImage() {
        return image;
    }

    public int getNewPrice() {
        return count * newPrice;
    }

    public String getAllCount() {
        return String.valueOf(count);
    }

    public Product(String name, String brand, String image) {
        this.name = name;
        this.brand = brand;
        this.image = image;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setCount(int count) {
        if (count >= 0) this.count = count;
    }
}
