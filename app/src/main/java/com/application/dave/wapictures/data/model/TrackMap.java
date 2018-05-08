package com.application.dave.wapictures.data.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davide-syn on 4/27/18.
 */

public class TrackMap extends RealmObject {
    @PrimaryKey
    String trackParamsKey;

    RealmList<Profile> trackList = new RealmList<>();

    public void setTrackList(RealmList<Profile> trackList) {
        this.trackList = trackList;
    }

    public void setParamsKey(String paramsKey) {
        this.trackParamsKey = paramsKey;
    }

    public List<Profile> getTrackList() {
        return trackList;
    }
}
