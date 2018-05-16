package com.application.dave.wapictures.data;

import android.content.Context;

import com.application.dave.wapictures.data.local.Local;
import com.application.dave.wapictures.data.local.TrackLocalDataSource;
import com.application.dave.wapictures.data.mock.Mock;
import com.application.dave.wapictures.data.mock.MockTrackLocalDataSource;
import com.application.dave.wapictures.data.remote.Remote;
import com.application.dave.wapictures.data.remote.TrackNetworkDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class ProfileRepositoryModule {
    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    @Local
    TrackDataSource provideProfileLocalDataSource(Realm realm) {
        return new TrackLocalDataSource(realm);
    }

    @Provides
    @Singleton
    @Remote
    TrackDataSource provideProfileRemoteDataSource() {
        return new TrackNetworkDataSource();
    }

    @Provides
    @Singleton
    @Mock
    TrackDataSource provideProfileMockDataSource(Context context) {
        return new MockTrackLocalDataSource(context);
    }

}
