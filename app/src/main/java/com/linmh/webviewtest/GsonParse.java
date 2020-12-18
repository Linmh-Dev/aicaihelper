package com.linmh.webviewtest;

import com.google.gson.Gson;

public class GsonParse {

    private static Gson gson;

    private GsonParse() {

    }

    public static synchronized Gson getInstance() {

            if (gson == null) {
                gson = new Gson();
            }

        return gson;
    }

}
