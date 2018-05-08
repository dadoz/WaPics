package com.application.dave.wapictures.tracklist;

import android.util.SparseArray;

import com.application.dave.wapictures.BasePresenter;
import com.application.dave.wapictures.data.model.Profile;

import java.util.List;

public interface TrackContract {

    interface TrackView {
        void onRenderData(List<Profile> items);
        void onError(String error);
        void showStandardLoading();
        void hideStandardLoading();
    }
    interface TrackPresenterInterface extends BasePresenter {
        void unsubscribe();
        void retrieveItems(SparseArray<String> params);
        void bindView(TrackView view);
        void deleteView();
    }
}