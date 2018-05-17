package com.application.dave.wapictures.data.local;


import android.util.Log;

import com.application.dave.wapictures.data.TrackDataSource;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.data.model.TrackMap;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
    private final Realm realm;

    @Inject
    public TrackLocalDataSource(Realm realm) {
        this.realm = realm;
    }
    /**
     *
     * @return
     */
    @Override
    public Observable<List<Profile>> getProfiles(String avatarId) {
        return query(avatarId).<TrackMap>asFlowable().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map(TrackMap::getTrackList);
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        TrackMap cache = query(paramsKey);
        return cache != null &&
                cache.getTrackList() != null &&
                cache.getTrackList().size() != 0;
    }

    @Override
    public void setTracks(List<Profile> trackList, String paramsKey) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
//        realm.executeTransaction(realm1 -> {
//            TrackMap trackMap = realm1.createObject(TrackMap.class, paramsKey);
//            trackMap.getTrackList().addAll(realm1.copyToRealmOrUpdate(trackList));
//        });
    }

    /**
     * query to handle tracks items
     * @param paramsKey
     * @return
     */
    private TrackMap query(String paramsKey) {
        return Realm.getDefaultInstance().where(TrackMap.class).equalTo("trackParamsKey", paramsKey)
                .findFirst();
    }
}
