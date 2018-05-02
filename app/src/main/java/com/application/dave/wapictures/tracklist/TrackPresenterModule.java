package com.application.dave.wapictures.tracklist;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TrackPresenterModule {
    @Binds
    abstract TrackContract.TrackPresenterInterface taskPresenter(TrackPresenter presenter);
}
