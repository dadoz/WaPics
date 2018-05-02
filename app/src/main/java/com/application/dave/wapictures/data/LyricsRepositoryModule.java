package com.application.dave.wapictures.data;

import com.application.dave.wapictures.data.local.Local;
import com.application.dave.wapictures.data.local.LyricsLocalDataSource;
import com.application.dave.wapictures.data.remote.LyricsNetworkDataSource;
import com.application.dave.wapictures.data.remote.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LyricsRepositoryModule {

    @Provides
    @Singleton
    @Local
    LyricsDataSource provideLyricsLocalDataSource() {
        return new LyricsLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    LyricsDataSource provideLyricsRemoteDataSource() {
        return new LyricsNetworkDataSource();
    }
}
