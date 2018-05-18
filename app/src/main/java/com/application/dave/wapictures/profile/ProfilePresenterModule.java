package com.application.dave.wapictures.profile;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ProfilePresenterModule {
    @Binds
    abstract ProfileContract.ProfilePresenterInterface taskPresenter(ProfilePresenter presenter);
}
