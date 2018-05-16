package com.application.dave.wapictures.data.mock;


import android.content.Context;

import com.application.dave.wapictures.data.TrackDataSource;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * In Ram memory storage
 */
@Singleton
public class MockTrackLocalDataSource implements TrackDataSource {
    private final List<Profile> profileData;

    @Inject
    public MockTrackLocalDataSource(Context context) {
        String json = Utils.readFileFromAssets(context.getAssets(), "profile_response_200.json");
        profileData = new Gson().fromJson(json, new TypeToken<List<Profile>>(){}.getType());
    }

    @Override
    public Observable<List<Profile>> getProfiles(String avatarId) {
        return Observable.just(profileData);
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return !profileData.isEmpty();
    }

    @Override
    public void setTracks(List<Profile> trackList, String paramsKey) {
    }
}
