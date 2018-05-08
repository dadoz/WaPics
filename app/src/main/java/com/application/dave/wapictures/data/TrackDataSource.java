package com.application.dave.wapictures.data;


import com.application.dave.wapictures.data.model.Track;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataSource {
    Observable<List<Track>> getAvatars(String avatarId);
    void setTracks(List<Track> stargazers, String paramsKey);
    boolean hasTracks(String paramsKey);
}
