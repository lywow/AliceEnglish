package com.alice.collector.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static Gson gson = new GsonBuilder().create();

    public static Gson getInstance() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }
}
