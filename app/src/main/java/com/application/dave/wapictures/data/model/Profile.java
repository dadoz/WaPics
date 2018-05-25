package com.application.dave.wapictures.data.model;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject {

    @PrimaryKey
    String id;
    @SerializedName("date")
    String date;
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

    public DateTime getDate() {
        return DateTime.parse(date);
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}