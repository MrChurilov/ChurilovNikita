package ru.test.rambler.churilovnikita.Network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ru.test.rambler.churilovnikita.Network.RecentResponce;

public class PhotoDeserializer implements JsonDeserializer<RecentResponce> {
    @Override
    public RecentResponce deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray data = json.getAsJsonObject().getAsJsonArray("data");
        return new RecentResponce(data);
    }
}
