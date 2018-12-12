package com.agira.bhinfotech.modal;

import com.agira.bhinfotech.modal.response.Home;
import com.agira.bhinfotech.utility.Utility;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Response {

    private static Gson gson = new Gson();

    public static Home getHome(String result) {

        Utility.log_d(result);
       return gson.fromJson(result, Home.class);
    }

}
