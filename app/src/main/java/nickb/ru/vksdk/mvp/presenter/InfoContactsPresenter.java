package nickb.ru.vksdk.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.consts.ApiConstants;
import nickb.ru.vksdk.model.Profile;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.MemberViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;
import nickb.ru.vksdk.rest.api.GroupsApi;
import nickb.ru.vksdk.rest.api.UsersApi;
import nickb.ru.vksdk.rest.model.request.GroupGetByIdRequestModel;
import nickb.ru.vksdk.rest.model.request.UsersGetRequestModel;

@InjectViewState
public class InfoContactsPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    GroupsApi mGroupApi;

    @Inject
    UsersApi mUserApi;

    public InfoContactsPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mGroupApi.getById(new GroupGetByIdRequestModel(ApiConstants.MY_GROUP_ID).toMap())
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .flatMap(group-> Observable.fromIterable(group.getContactList()))
                .flatMap(contact -> mUserApi.get(new UsersGetRequestModel(String.valueOf(contact.getUserId())).toMap()))
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .doOnNext(profile -> profile.setContact(true))
                .doOnNext(this::saveToDb)
                .flatMap(profile -> Observable.fromIterable(parsePojoModel(profile)));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(profile -> Observable.fromIterable(parsePojoModel(profile)));

    }

    private List<BaseViewModel> parsePojoModel(Profile profile) {
        List<BaseViewModel> items = new ArrayList<>();
        items.add(new MemberViewModel(profile));

        return items;
    }

    private List<BaseViewModel> parsePojoModel(List<Profile> profileList) {
        List<BaseViewModel> items = new ArrayList<>();
        for (Profile profile : profileList) {
            items.addAll(parsePojoModel(profile));
        }

        return items;
    }

    public Callable<List<Profile>> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            List<Profile> result = realm.where(Profile.class)
                    .equalTo("isContact", true)
                    .findAll();
            return realm.copyFromRealm(result);
        };
    }

}
