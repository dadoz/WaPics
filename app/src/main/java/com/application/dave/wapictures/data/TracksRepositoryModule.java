package com.application.dave.wapictures.data;

import com.application.dave.wapictures.data.local.Local;
import com.application.dave.wapictures.data.local.TrackLocalDataSource;
import com.application.dave.wapictures.data.remote.Remote;
import com.application.dave.wapictures.data.remote.TrackNetworkDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class TracksRepositoryModule {
    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    @Local
    TrackDataSource provideStargazerLocalDataSource(Realm realm) {
        return new TrackLocalDataSource(realm);
    }

    @Provides
    @Singleton
    @Remote
    TrackDataSource provideStargazerRemoteDataSource() {
        return new TrackNetworkDataSource();
    }

}
