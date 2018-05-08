package com.application.dave.wapictures.data;

import android.content.Context;

import com.application.dave.wapictures.data.local.Local;
import com.application.dave.wapictures.data.model.Lyric;
import com.application.dave.wapictures.data.remote.Remote;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

public class LyricsRepository {

    private final LyricsDataSource localDataSource;
    private final LyricsDataSource networkDataSource;

    @Inject
    LyricsRepository(Context context, @Local LyricsDataSource localDataSource, @Remote LyricsDataSource networkDataSource) {
        this.localDataSource = localDataSource;
        this.networkDataSource = networkDataSource;
    }

    /**
     * get cached or network data
     * @return
     */
    public Observable<Lyric> getLyrics(String trackId) {
        if (localDataSource.hasLyrics(trackId)) {
            //show data from cache
            return localDataSource.getLyrics(trackId, "");
        }

        //show data from netwkor and added on cache if some result
        return networkDataSource.getLyrics(trackId, "")
                .doOnNext(items -> localDataSource.setLyrics(items, trackId));
    }

    public void refreshCache() {
        //TODO implement it
    }
}
