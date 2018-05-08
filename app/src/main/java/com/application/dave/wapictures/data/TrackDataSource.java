package com.application.dave.wapictures.data;


import com.application.dave.wapictures.data.model.Profile;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataSource {
    Observable<List<Profile>> getProfiles(String avatarId);
    void setTracks(List<Profile> stargazers, String paramsKey);
    boolean hasTracks(String paramsKey);
}
