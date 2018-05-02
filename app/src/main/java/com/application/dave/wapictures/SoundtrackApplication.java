package com.application.dave.wapictures;

import com.application.dave.wapictures.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;

public class SoundtrackApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return getComponent();
    }

    /**
     * get componente base builder
     * @return
     */
    public AndroidInjector<? extends DaggerApplication> getComponent() {
        //TODO move it
        Realm.init(this);

        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
