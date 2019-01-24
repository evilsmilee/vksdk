package nickb.ru.vksdk.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import nickb.ru.vksdk.common.manager.NetworkManager;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;

public abstract class BaseFeedPresenter<V extends BaseFeedView> extends MvpPresenter<BaseFeedView> {

    private static final int START_PAGE_SIZE = 15;
    private static final int NEXT_PAGE_SIZE = 15;

    private boolean mIsInLoading;

    @Inject
    NetworkManager mNetworkManager;

    @SuppressLint("CheckResult")
    private void loadData(ProgressType progressType, int offset, int count) {
        if(mIsInLoading) {
            return;
        }
        mIsInLoading = true;

        mNetworkManager.getNetworkObservable()
                .flatMap(aBoolean -> {
                    if (!aBoolean && offset >  0) {
                        return Observable.empty();
                    }
                    return aBoolean
                            ? onCreateLoadDataObservable(count, offset)
                            : onCreateRestoreDataObservable();
                })


                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadStart(progressType);
                })
                .doFinally(() -> {
                    onLoadFinish(progressType);
                })
                .subscribe(repositories -> {
                    onLoadingSuccess(progressType, repositories);
                }, error -> {
                    error.printStackTrace();
                    onLoadingFailed(error);
                });
    }

    abstract Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset);

    public enum ProgressType {
        Refreshing, ListProgress, Paging
    }

    private void showProgress(ProgressType progressType) {
        switch (progressType) {
            case Refreshing:
                getViewState().showRefreshing();
                break;
            case ListProgress:
                getViewState().showListProgress();
                break;
        }
    }

    private void hideProgress(ProgressType progressType) {
        switch (progressType) {
            case Refreshing:
                getViewState().hideRefreshing();
                break;
            case ListProgress:
                getViewState().hideListProgress();
                break;
        }
    }

    public void loadStart() {
        loadData(ProgressType.ListProgress, 0, START_PAGE_SIZE);
    }

    public void loadNext(int offset) {
        loadData(ProgressType.Paging, offset, NEXT_PAGE_SIZE);
    }

    public void loadRefresh() {
        loadData(ProgressType.Refreshing, 0, START_PAGE_SIZE);
    }

    public void onLoadStart(ProgressType progressType) {
        showProgress(progressType);
    }

    public void onLoadFinish(ProgressType progressType) {
        mIsInLoading = false;
        hideProgress(progressType);
    }

    public void onLoadingFailed(Throwable throwable) {
        getViewState().showError(throwable.getMessage());
    }

    public void onLoadingSuccess(ProgressType progressType, List<BaseViewModel> items) {
        if(progressType == ProgressType.Paging) {
            getViewState().addItems(items);
        } else {
            getViewState().setItems(items);
        }
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservable();

}
