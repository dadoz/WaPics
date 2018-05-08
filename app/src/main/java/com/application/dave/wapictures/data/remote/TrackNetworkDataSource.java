package com.application.dave.wapictures.data.remote;

import com.application.dave.wapictures.data.TrackDataSource;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.data.remote.services.RetrofitServiceRx;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

@Singleton
public class TrackNetworkDataSource extends RetrofitDataSourceBase implements TrackDataSource {
    /**
     *
     * @param avatarId
     * @return
     */
    public Observable<List<Profile>> getProfiles(String avatarId) {
        return new RetrofitServiceRx().getSoundtrackRetrofit()
                .getAvatars(avatarId)
                .compose(handleRxErrorsTransformer());
    }

    /**
     * @param stargazers
     */
    @Override
    public void setTracks(List<Profile> stargazers, String paramsKey) {
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return false;
    }
}
