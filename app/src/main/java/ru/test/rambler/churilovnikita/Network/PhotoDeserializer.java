package ru.test.rambler.churilovnikita.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PhotoDeserializer implements JsonDeserializer<RecentResponce> {
    @Override
    public RecentResponce deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray data = json.getAsJsonObject().getAsJsonArray("data");
        return new RecentResponce(data);
    }
}
