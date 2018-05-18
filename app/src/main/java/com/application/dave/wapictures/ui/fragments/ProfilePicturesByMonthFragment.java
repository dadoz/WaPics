package com.application.dave.wapictures.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.dave.wapictures.R;
import com.application.dave.wapictures.data.base.SimpleSectionedRecyclerViewAdapter;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.profile.ItemViewHolder;
import com.application.dave.wapictures.profile.ProfileContract;

import java.util.List;

import butterknife.BindView;

public class ProfilePicturesByMonthFragment extends Fragment implements ProfileContract.ProfileView {
    @BindView(R.id.profileSectionedByMonthRecyclerViewId)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_pictures_by_month_layout, container, false);
    }

    @Override
    public void onRenderData(List<Profile> items) {
        initRecyclerView(items);
    }

    private void initRecyclerView(List<Profile> items) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(getLayoutInflater()
                .inflate(R.layout.track_item, ((ViewGroup) getView()), false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SimpleSectionedRecyclerViewAdapter(items, itemViewHolder));
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
}
