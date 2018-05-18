package com.application.dave.wapictures.profile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.dave.wapictures.R;
import com.application.dave.wapictures.data.base.sectionedRvAdapter.BaseItemViewHolder;
import com.application.dave.wapictures.data.model.Profile;

/**
 * Track view holder
 */
public class ItemViewHolder extends BaseItemViewHolder<Profile> {
    private final TextView artistNameTextview;
    private final TextView trackNameTextview;
    private final ImageView avatarImageView;

    /**
     *
     * @param view
     */
    public ItemViewHolder(View view) {
        super(view);
        artistNameTextview =  view.findViewById(R.id.artistNameTextViewId);
        trackNameTextview =  view.findViewById(R.id.trackNameTextViewId);
        avatarImageView =  view.findViewById(R.id.avatarImageViewId);
    }

    @Override
    public void render(Profile item) {
    }


}