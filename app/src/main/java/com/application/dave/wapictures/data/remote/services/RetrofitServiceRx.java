package com.application.dave.wapictures.data.remote.services;


import com.application.dave.wapictures.BuildConfig;
import com.application.dave.wapictures.data.model.Lyric;
import com.application.dave.wapictures.data.remote.services.gson.LyricsJsonDeserializer;
import com.google.gson.GsonBuilder;

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
//                            .registerTypeAdapter(new TypeToken<List<Profile>>(){}.getType(), new TrackJsonDeserializer())
                            .registerTypeAdapter(Lyric.class, new LyricsJsonDeserializer())
                            .create()))
                    .build()
                    .create(TracksService.class);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }
}
