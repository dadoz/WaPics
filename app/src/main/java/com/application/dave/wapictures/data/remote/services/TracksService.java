package com.application.dave.wapictures.data.remote.services;


import com.application.dave.wapictures.data.model.Lyric;
import com.application.dave.wapictures.data.model.Profile;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TracksService {
    @GET("avatars/{id}")
    Observable<List<Profile>> getAvatars(@Path("id") String id);

    @GET("track.lyrics.get")
    Observable<Lyric> getLyrics(@Query("track_id") String trackId, @Query("apikey") String apiKey);
}
