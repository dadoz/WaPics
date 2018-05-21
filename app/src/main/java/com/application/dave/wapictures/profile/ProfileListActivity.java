package com.application.dave.wapictures.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.application.dave.wapictures.R;
import com.application.dave.wapictures.data.model.Profile;
import com.application.dave.wapictures.lyric.LyricActivity;
import com.application.dave.wapictures.ui.EmptyView;
import com.application.dave.wapictures.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

import static com.application.dave.wapictures.MainActivity.TRACK_PARAMS_KEY;

/**
 * stargazer activity
 */
public class ProfileListActivity extends DaggerAppCompatActivity implements ProfileContract.ProfileView,
        ProfileListAdapter.OnTrackItemClickListener, ProfileListAdapter.OnTrackLoadMoreClickListener {
    public static final String LYRICS_PARAMS_KEY = "LYRICS_PARAMS_KEY";
    @BindView(R.id.trackRecyclerViewId)
    RecyclerView recyclerView;
    @BindView(R.id.trackProgressbarId)
    ProgressBar progressBar;
    @BindView(R.id.emptyViewId)
    EmptyView emptyView;

    @Inject
    ProfilePresenter presenter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        unbinder = ButterKnife.bind(this);
        onInitView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    /**
     * iit view and retrieve stargazers data
     */
    private void onInitView() {
        initActionbar();
        presenter.bindView(this);
        presenter.retrieveItems(new SparseArray<>());//Utils.getTrackParamsFromBundle(getIntent().getExtras().getBundle(TRACK_PARAMS_KEY)));
    }

    /**
     * TODO mv to base activity
     * actionbar set listener and back arrow
     */
    private void initActionbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * todo mv on base
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRenderData(List<Profile> items) {
        presenter.profileSubject.onNext(items);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initRecyclerView(items);
    }

//    private void initProfileExpandedRv(List<Profile> items) {
//        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
//        profileExpandedRecyclerView.setLayoutManager(layoutManager);
//        profileExpandedRecyclerView.setAdapter(new ProfileListAdapter(items, this, this));
//    }


    @Override
    public void onError(String error) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        Snackbar.make(findViewById(R.id.activity_main), R.string.retrieve_error,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showStandardLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideStandardLoading() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * init recycler view binding data by adapter
     * @param items
     */
    private void initRecyclerView(List<Profile> items) {
        if (items.size() == 0) {
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ProfileListAdapter(items, this, this));
     }

    @Override
    public void onTrackItemClick(View view, Profile track) {
//        Intent intent = LyricActivity.buildIntent(this, track.getTrackId(), track.getArtistName(),
//                track.getTrackName(), track.getAlbumCoverart100x100());
        Intent intent = new Intent(this, LyricActivity.class);
        startActivity(intent);

    }

    /**
     *
     * @param context
     * @param country
     * @param pageSize
     * @param hasLyricsCheckbox
     * @param initialPage
     * @return
     */
    public static Intent buildIntent(Context context, String country, String pageSize, String hasLyricsCheckbox, String initialPage) {
        Bundle bundle = Utils.buildTrackParams(country, pageSize, hasLyricsCheckbox, initialPage);
        Intent intent = new Intent(context, ProfileListActivity.class);
        intent.putExtra(TRACK_PARAMS_KEY, bundle);
        return intent;
    }

    @Override
    public void onTrackLoadMoreClick(View view) {
        presenter.retrieveMoreItems();
    }
}
