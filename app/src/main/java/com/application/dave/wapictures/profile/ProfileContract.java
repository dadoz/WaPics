package com.application.dave.wapictures.profile;

import android.util.SparseArray;

import com.application.dave.wapictures.BasePresenter;
import com.application.dave.wapictures.data.model.Profile;

import java.util.List;

public interface ProfileContract {

    interface ProfileView {
        void onRenderData(List<Profile> items);
        void onError(String error);
        void showStandardLoading();
        void hideStandardLoading();
    }
    interface ProfilePresenterInterface extends BasePresenter {
        void unsubscribe();
        void retrieveItems(SparseArray<String> params);
        void bindView(ProfileView view);
        void deleteView();
    }
}