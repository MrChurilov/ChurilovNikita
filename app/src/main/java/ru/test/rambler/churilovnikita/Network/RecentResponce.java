package ru.test.rambler.churilovnikita.network;

import com.google.gson.JsonArray;

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
