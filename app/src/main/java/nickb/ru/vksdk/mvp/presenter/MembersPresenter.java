package nickb.ru.vksdk.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.consts.ApiConstants;
import nickb.ru.vksdk.model.Member;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.MemberViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;
import nickb.ru.vksdk.rest.api.GroupsApi;
import nickb.ru.vksdk.rest.model.request.GroupsGetMembersRequestModel;


@InjectViewState
public class MembersPresenter extends  BaseFeedPresenter<BaseFeedView> {
    @Inject
    GroupsApi mGroupApi;

    public MembersPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mGroupApi.getMembers(new GroupsGetMembersRequestModel(
                ApiConstants.MY_GROUP_ID, count, offset).toMap())
                .flatMap(baseItemResponseFull -> {
                    return Observable.fromIterable(baseItemResponseFull.response.getItems());
                })
                .doOnNext(member -> saveToDb(member))
                .map(member -> new MemberViewModel(member));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .map(member -> new MemberViewModel(member));
    }



    public Callable<List<Member>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {Member.ID};
            Sort[] sortOrder = {Sort.ASCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Member> results = realm.where(Member.class)
                    .sort(sortFields, sortOrder)
                    .findAll();
            return realm.copyFromRealm(results);
        };
    }
}
