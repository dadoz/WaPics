package com.application.dave.wapictures.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.application.dave.wapictures.R;
import com.application.dave.wapictures.data.base.sectionedRvAdapter.SimpleSectionedRecyclerViewAdapterAbs;
import com.application.dave.wapictures.data.model.Profile;

import org.joda.time.Months;

import java.util.List;

public class MonthlyRVAdapter extends SimpleSectionedRecyclerViewAdapterAbs<ItemViewHolder, Profile> {
    public MonthlyRVAdapter(List<Profile> items) {
        super(items);
    }

    @Override
    protected boolean onPlaceSubHeaderCondition(Profile profile1, Profile profile2) {
        return Months.monthsBetween(profile2.getDate(), profile1.getDate()).getMonths() > 0;
    }

    @Override
    protected ItemViewHolder onCreateItemViewHolderAbs(ViewGroup parent) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, parent, false));
    }

    @Override
    protected String getSubHeaderLabel(int nextItemPosition) {
        return items.get(nextItemPosition).getDate().toString("MMMM");
    }
}
