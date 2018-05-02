package com.application.dave.wapictures.data.local;

import com.application.dave.wapictures.data.LyricsDataSource;
import com.application.dave.wapictures.data.model.Lyric;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * In Ram memory storage
 */
@Singleton
public class LyricsLocalDataSource implements LyricsDataSource {
    private Map<String, Lyric> map = new HashMap<>();

    @Override
    public boolean hasLyrics(String trackId) {
        return map.get(trackId) != null;
    }

    /**
     *
     * @param trackId
     * @param apiKey
     * @return
     */
    @Override
    public Observable<Lyric> getLyrics(String trackId, String apiKey) {
        return Observable.just(map.get(trackId));
    }

    public void setLyrics(Lyric lyric, String trackId) {
        this.map.put(trackId, lyric);
    }
}
