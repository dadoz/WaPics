package com.application.dave.wapictures.data.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.dave.wapictures.R;
import com.application.dave.wapictures.data.base.sectionedRvAdapter.BaseItemViewHolder;
import com.application.dave.wapictures.data.base.sectionedRvAdapter.SectionedRecyclerViewAdapter;
import com.application.dave.wapictures.profile.ItemViewHolder;

import java.util.List;

public class SimpleSectionedRecyclerViewAdapter<H extends BaseItemViewHolder, T>
        extends SectionedRecyclerViewAdapter<SimpleSectionedRecyclerViewAdapter.SubheaderViewHolder, H> {
    private final List<T> items;

    public SimpleSectionedRecyclerViewAdapter(List<T> items) {
        this.items = items;
    }
    @Override
    public boolean onPlaceSubheaderBetweenItems(int position) {
        //return true if you want to place subheader between two neighboring items
        return true;
    }

    @Override
    public SubheaderViewHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.month_section_layout_id, parent, false));
    }

    @Override
    public H onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return (H) new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, null, false));

    }

    @Override
    public int getItemSize() {
        return items.size();
    }

    @Override
    public void onBindItemViewHolder(H holder, int position) {
        //Setup item view
        holder.render(items.get(position));
    }

    @Override
    public void onBindSubheaderViewHolder(RecyclerView.ViewHolder subheaderHolder, int nextItemPosition) {
        ((SubheaderViewHolder) subheaderHolder).titleTextView.setText("balblallbla");
    }

    /**
     *
     */
    public class SubheaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;

        public SubheaderViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.subheader_textview_id);
            //Setup holder
        }
    }



//
//    /**
//     *
//     */
//    public static class ItemViewHolder extends RecyclerView.ViewHolder {
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

}
