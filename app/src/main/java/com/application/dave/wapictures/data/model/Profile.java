package com.application.dave.wapictures.data.model;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject {

    @PrimaryKey
    String id;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("name")
    String name;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }
}