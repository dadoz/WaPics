package com.application.dave.wapictures.profile;

import android.util.Log;
import android.util.SparseArray;

import com.application.dave.wapictures.data.ProfileRepository;
import com.application.dave.wapictures.data.model.Profile;

import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

import static io.reactivex.internal.operators.single.SingleInternalHelper.toObservable;

@Singleton
public class ProfilePresenter implements ProfileContract.ProfilePresenterInterface {
    private static final String TAG = "TrackPresenter";
    private static WeakReference<ProfileContract.ProfileView> trackView;
    private final ProfileRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected ProgressLoader loader;
    private SparseArray<String> params;

    public BehaviorSubject<List<Profile>> profileSubject = BehaviorSubject.create();

    @Inject
    ProfilePresenter(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    /**
     * bind view to presenter
     * @param view
     */
    @Override
    public void bindView(ProfileContract.ProfileView view) {
        trackView = new WeakReference<>(view);
        loader = new ProgressLoader(
                view::showStandardLoading,
                view::hideStandardLoading);
    }

    /**
     * delete view
     */
    @Override
    public void deleteView() {
        trackView.clear();
    }

    /**
     * params
     * 0 -> page
     * 1 -> pageSize
     * 2 -> country
     * 3 -> hasLyrics
     * 
     * retrieve item obs
     * @param params
     */
    @Override
    public void retrieveItems(SparseArray<String> params) {
        Log.e(TAG, params.toString());
        //set params
        this.params = params;

        //build obs
        compositeDisposable.add(repository
                .getTracks(params.get(0), params.get(1), params.get(2), params.get(3))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(composeLoaderTransformer(loader))
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        items -> trackView.get().onRenderData(items),
                        error -> trackView.get().onError(error.getMessage())));
    }


    /**
     * TODO mv to BASE
     * compose loader transformer
     * @param loader
     * @param <T>
     * @return
     */
    <T extends List>ObservableTransformer<T, T> composeLoaderTransformer(ProgressLoader loader) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> loader.show.run())
                .doOnError(error -> loader.hide.run())
                .doOnNext(res -> loader.hide.run());
    }

    public SparseArray<String> getParams() {
        return params;
    }

    /**
     *
     */
    public void retrieveMoreItems() {
        int nextPage = Integer.parseInt(params.get(0)) + 1;
        params.setValueAt(0, Integer.toString(nextPage));
        retrieveItems(params);
    }

    public Observable<List<Profile>> getProfileSubject() {
        return profileSubject
                .map(list -> {
                    list.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
                    return list;
                });
    }

    /**
     * progress loader
     */
    class ProgressLoader {
        Action show;
        Action hide;

        ProgressLoader(Action show, Action hide) {
            this.show = show;
            this.hide = hide;
        }
    }

}
