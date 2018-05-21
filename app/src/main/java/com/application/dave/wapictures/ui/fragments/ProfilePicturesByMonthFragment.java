package com.application.dave.wapictures.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.dave.wapictures.R;
import com.application.dave.wapictures.data.base.SimpleSectionedRecyclerViewAdapter;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.profile.ProfileContract;
import com.application.dave.wapictures.profile.ProfilePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.Disposable;

public class ProfilePicturesByMonthFragment extends DaggerFragment implements ProfileContract.ProfileView {
    @BindView(R.id.profileSectionedByMonthRecyclerViewId)
    RecyclerView recyclerView;
    private Unbinder unbider;
    private Disposable disposable;

    @Inject
    ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_pictures_by_month_layout, container, false);
        unbider = ButterKnife.bind(this, view);
        subscribeOnSubject();
        return view;
    }

    private void subscribeOnSubject() {
        disposable = presenter.profileSubject.subscribe(this::initRecyclerView);
    }

    @Override
    public void onRenderData(List<Profile> items) {
        initRecyclerView(items);
    }

    private void initRecyclerView(List<Profile> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SimpleSectionedRecyclerViewAdapter(items));
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void showStandardLoading() {

    }

    @Override
    public void hideStandardLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbider != null)
            unbider.unbind();

        if (disposable != null)
            disposable.dispose();
    }
}
