package com.application.mxm.soundtracks.data.remote.services;


import com.application.mxm.soundtracks.BuildConfig;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.services.gson.LyricsJsonDeserializer;
import com.application.mxm.soundtracks.data.remote.services.gson.TrackJsonDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceRx {
    /**
     * get service
     * @return
     */
    public TracksService getSoundtrackRetrofit() {
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.MXM_BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(new TypeToken<List<Track>>(){}.getType(), new TrackJsonDeserializer())
                            .registerTypeAdapter(Lyric.class, new LyricsJsonDeserializer())
                            .create()))
                    .build()
                    .create(TracksService.class);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}
