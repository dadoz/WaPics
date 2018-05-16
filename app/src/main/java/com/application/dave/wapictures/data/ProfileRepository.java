package com.application.dave.wapictures.data;

import com.application.dave.wapictures.data.local.Local;
import com.application.dave.wapictures.data.mock.Mock;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by davide-syn on 4/24/18.
 */

public class ProfileRepository {

    private final TrackDataSource localDataSource;
    private final TrackDataSource networkDataSource;

    @Inject
    ProfileRepository(@Local TrackDataSource localDataSource, @Mock TrackDataSource networkDataSource) {
        this.localDataSource = localDataSource;
        this.networkDataSource = networkDataSource;
    }

    /**
     * get cached or network data
     * @return
     */
    public Observable<List<Profile>> getTracks(String page, String pageSize, String country, String fHasLyrics) {
        //set params key
        String paramsKey = Utils.getTrackParamsKey(page, pageSize, country, fHasLyrics);

        //show data from cache
//        if (localDataSource.hasTracks(paramsKey)) {
//            return localDataSource.getTracks(page, pageSize, country, fHasLyrics, BuildConfig.API_KEY);
//        }

        //show data from netwkor and added on cache if some result
        return networkDataSource
                .getProfiles(page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(list -> localDataSource.setTracks(list, paramsKey));
    }

    public void refreshCache() {
        //TODO implement it
    }
}
