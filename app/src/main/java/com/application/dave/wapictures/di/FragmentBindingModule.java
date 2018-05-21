package com.application.dave.wapictures.di;

import com.application.dave.wapictures.ui.fragments.ProfilePicturesByMonthFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector()
    public abstract ProfilePicturesByMonthFragment getFrag();
}
