package com.application.dave.wapictures.di;

import android.app.Application;

import com.application.dave.wapictures.SoundtrackApplication;
import com.application.dave.wapictures.data.LyricsRepositoryModule;
import com.application.dave.wapictures.data.ProfileRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        LyricsRepositoryModule.class,
        ProfileRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<SoundtrackApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
