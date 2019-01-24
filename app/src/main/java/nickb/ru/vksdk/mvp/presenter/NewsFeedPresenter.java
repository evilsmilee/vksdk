package nickb.ru.vksdk.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import nickb.ru.vksdk.CurrentUser;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.common.utils.VKListHelper;
import nickb.ru.vksdk.consts.ApiConstants;
import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.NewsItemBodyViewModel;
import nickb.ru.vksdk.model.view.NewsItemFooterViewModel;
import nickb.ru.vksdk.model.view.NewsItemHeaderViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;
import nickb.ru.vksdk.rest.api.WallApi;
import nickb.ru.vksdk.rest.model.request.WallGetRequestModel;


@InjectViewState
public class NewsFeedPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    WallApi mWallApi;

    private boolean enableFiltering = false;

    public NewsFeedPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
       return  mWallApi.get(new WallGetRequestModel(ApiConstants.MY_GROUP_ID, count, offset).toMap())
               .flatMap(full -> Observable.fromIterable(VKListHelper.getWallList(full.response)))
               .compose(applyFilter())
               .doOnNext(this::saveToDb)
               .flatMap(wallItem -> {
                   List<BaseViewModel> baseItems = new ArrayList<>();
                   baseItems.add(new NewsItemHeaderViewModel(wallItem));
                   baseItems.add(new NewsItemBodyViewModel(wallItem));
                   baseItems.add(new NewsItemFooterViewModel(wallItem));
                   return Observable.fromIterable(baseItems);
               });
              /* .toList()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(objects -> mAdapter.addItems(objects));*/
    }


    public Callable<List<WallItem>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {"date"};
            Sort[] sortOrder = {Sort.DESCENDING};
            Realm realm = Realm.getDefaultInstance();
            RealmResults<WallItem> realmResults = realm.where(WallItem.class)
                    .sort(sortFields, sortOrder)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .compose(applyFilter())
                .flatMap(wallItem -> Observable.fromIterable(parsePojoModel(wallItem)));
    }

    private List<BaseViewModel> parsePojoModel(WallItem wallItem) {
        List<BaseViewModel> baseItems = new ArrayList<>();
        baseItems.add(new NewsItemHeaderViewModel(wallItem));
        baseItems.add(new NewsItemBodyViewModel(wallItem));
        baseItems.add(new NewsItemFooterViewModel(wallItem));

        return baseItems;
    }


    public void setEnableFiltering(boolean enableFiltering) {
        this.enableFiltering = enableFiltering;
    }

    protected ObservableTransformer<WallItem, WallItem> applyFilter() {
        if(enableFiltering && CurrentUser.getId() != null) {
            return baseItemObservable -> baseItemObservable.filter(
                    wallItem ->  CurrentUser.getId().equals(String.valueOf(wallItem.getFromId()))
            );
        } else {
            return baseItemObservable -> baseItemObservable;
        }
    }

}
