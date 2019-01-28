package nickb.ru.vksdk.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import nickb.ru.vksdk.CurrentUser;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.common.manager.NetworkManager;
import nickb.ru.vksdk.model.Profile;
import nickb.ru.vksdk.mvp.view.MainView;
import nickb.ru.vksdk.rest.api.UsersApi;
import nickb.ru.vksdk.rest.model.request.UsersGetRequestModel;
import nickb.ru.vksdk.ui.fragment.BaseFragment;
import nickb.ru.vksdk.ui.fragment.BoardFragment;
import nickb.ru.vksdk.ui.fragment.InfoFragment;
import nickb.ru.vksdk.ui.fragment.MembersFragment;
import nickb.ru.vksdk.ui.fragment.MyPostsFragment;
import nickb.ru.vksdk.ui.fragment.NewsFeedFragment;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    MyFragmentManager myFragmentManager;

    @Inject
    UsersApi mUserApi;

    @Inject
    NetworkManager mNetworkManager;


    public void checkAuth() {
        if(!CurrentUser.isAuthorized()) {
            getViewState().startSignIn();
        } else {
            getCurrentUser();
            getViewState().signedId();
        }
    }

    public MainPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    public Observable<Profile> getProfileFromNetwork() {
        return mUserApi.get(new UsersGetRequestModel(CurrentUser.getId()).toMap())
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .doOnNext(this::saveToDb);
    }

    private Observable<Profile> getProfileFromDb() {
        return Observable.fromCallable(getListFromRealmCallable());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Callable<Profile> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            Profile realmResults = realm.where(Profile.class)
                    .equalTo("id", Integer.parseInt(CurrentUser.getId()))
                    .findFirst();
            return realm.copyFromRealm(realmResults);
        };
    }

    @SuppressLint("CheckResult")
    private void getCurrentUser() {
        mNetworkManager.getNetworkObservable()
                .flatMap(aBoolean -> {
                    if(!CurrentUser.isAuthorized()) {
                        getViewState().startSignIn();
                    }
                    return aBoolean
                            ? getProfileFromNetwork()
                            : getProfileFromDb();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    getViewState().showCurrentUser(profile);
                }, error -> {
                    error.printStackTrace();
                });
    }

    public void drawerItemClick(int id) {
        BaseFragment fragment = null;

        switch (id) {
            case 1:
                fragment = new NewsFeedFragment();
                break;
            case 2:
                fragment = new MyPostsFragment();
                break;
            case 4:
                fragment = new MembersFragment();
                break;
            case 5:
                fragment = new BoardFragment();
                break;
            case 6:
                fragment = new InfoFragment();
                break;

        }

        if(fragment != null && !myFragmentManager.isAlreadyContaints(fragment)) {
            getViewState().showFragmentFromDrawer(fragment);
        }
    }

}
