package ru.test.rambler.churilovnikita.Network;

import com.google.gson.JsonArray;


import java.util.ArrayList;

public class RecentResponce {
    private JsonArray data;

    public RecentResponce(JsonArray data) {
        this.data = data;
    }

    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
    }

    public RecentResponce() {

    }


}
