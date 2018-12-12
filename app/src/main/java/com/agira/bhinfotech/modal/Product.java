package com.agira.bhinfotech.modal;

import java.io.Serializable;

public class Product implements Serializable {

    private int count = 0;

    private double price;

    private double discount;

    private double final_price;

    private String id;

    private String title;

    private String image;

    private String status;

    private String created;

    private String subtitle;


    public String getPrice() {
        return "<del style=\"color:red;\"> &#8377;" + price + "</del> <strong> &#8377; <ins>" + final_price + "</ins>!</strong>";
    }

    public String getAllPrice() {

        double total = count * final_price;
        return String.format("\u20B9 %s", total);
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getBrand() {
        return subtitle;
    }

    public double getNewPrice() {
        return count * final_price;
    }

    public String getAllCount() {
        return String.valueOf(count);
    }

    public void setCount(int count) {
        if (count >= 0) this.count = count;
    }
}
