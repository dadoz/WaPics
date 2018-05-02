package com.application.mxm.soundtracks.data.remote;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.services.RetrofitServiceRx;

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
     * @param page
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @param apiKey
     * @return
     */
    public Observable<List<Track>> getTracks(String page, String pageSize, String country, String fHasLyrics, String apiKey) {
        return new RetrofitServiceRx().getSoundtrackRetrofit()
                .getTracks(page, pageSize, country, fHasLyrics, apiKey)
                .compose(handleRxErrorsTransformer());
    }

    /**
     * @param stargazers
     */
    @Override
    public void setTracks(List<Track> stargazers, String paramsKey) {
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return false;
    }
}
