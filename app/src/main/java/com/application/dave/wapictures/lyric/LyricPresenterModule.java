package com.application.dave.wapictures.lyric;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LyricPresenterModule {
    @Binds
    abstract LyricContract.LyricsPresenterInterface lyricsPresenter(LyricPresenter presenter);
}
