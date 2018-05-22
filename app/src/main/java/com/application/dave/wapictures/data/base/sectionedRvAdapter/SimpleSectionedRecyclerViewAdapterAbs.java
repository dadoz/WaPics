package com.application.dave.wapictures.data.base.sectionedRvAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.dave.wapictures.R;

import java.util.List;

public abstract class SimpleSectionedRecyclerViewAdapterAbs<H extends BaseItemViewHolder, T>
        extends SectionedRecyclerViewAdapter<SimpleSectionedRecyclerViewAdapterAbs.SubheaderViewHolder, H> {
    protected final List<T> items;

    public SimpleSectionedRecyclerViewAdapterAbs(List<T> items) {
        this.items = items;
    }
    @Override
    public boolean onPlaceSubheaderBetweenItems(int position) {
        return position > 0 && onPlaceSubHeaderCondition(items.get(position), items.get(position -1));
    }

    @Override
    public SubheaderViewHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.month_section_layout_id, parent, false));
    }

    @Override
    public H onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return onCreateItemViewHolderAbs(parent);

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
        ((SubheaderViewHolder) subheaderHolder).titleTextView.setText(getSubHeaderLabel(nextItemPosition));
    }


    /**
     * subheader view holder
     */
    public class SubheaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;

        public SubheaderViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.subheader_textview_id);
            //Setup holder
        }
    }

    /**
     *
     * @param t
     * @param t1
     * @return
     */
    protected abstract boolean onPlaceSubHeaderCondition(T t, T t1);

    /**
     *
     * @param parent
     * @return
     */
    protected abstract H onCreateItemViewHolderAbs(ViewGroup parent);

    /**
     *
     * @param nextItemPosition
     * @return
     */
    protected abstract String getSubHeaderLabel(int nextItemPosition);

}
