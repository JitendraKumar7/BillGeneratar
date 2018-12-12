package com.agira.bhinfotech.modal.response;

import com.agira.bhinfotech.modal.Product;
import com.agira.bhinfotech.utility.Utility;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Home {

    private int status;

    private String msg;

    private Product[] result;

    public String getMsg() {

        return msg;
    }

    public boolean isStatus() {

        return status == 200;
    }

    public List<Product> getProduct() {

        return Arrays.asList(result);
    }
}
