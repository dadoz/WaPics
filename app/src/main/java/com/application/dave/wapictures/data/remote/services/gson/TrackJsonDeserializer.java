package com.application.dave.wapictures.data.remote.services.gson;

import com.application.dave.wapictures.data.model.Profile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * track json to a list of track item
 */
public class TrackJsonDeserializer implements JsonDeserializer<List<Profile>> {
    @Override
    public List<Profile> deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
        JsonArray itemArray = json.getAsJsonObject().get("message").getAsJsonObject().get("body").getAsJsonObject()
                .get("track_list").getAsJsonArray();

        List<Profile> list = new ArrayList<>();
        for (JsonElement item : itemArray) {
            list.add(new Gson().fromJson(item.getAsJsonObject().get("track").getAsJsonObject(), Profile.class));
        }
        return list;
    }
}