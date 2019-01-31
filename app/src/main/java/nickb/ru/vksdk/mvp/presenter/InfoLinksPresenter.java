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
import nickb.ru.vksdk.model.Group;
import nickb.ru.vksdk.model.attachment.Link;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.attachment.LinkAttachmentViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;
import nickb.ru.vksdk.rest.api.GroupsApi;
import nickb.ru.vksdk.rest.model.request.GroupGetByIdRequestModel;


@InjectViewState
public class InfoLinksPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    GroupsApi mGroupApi;

    public InfoLinksPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mGroupApi.getById(new GroupGetByIdRequestModel(ApiConstants.MY_GROUP_ID).toMap())
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .doOnNext(this::saveToDb)
                .flatMap(group -> Observable.fromIterable(parsePojoModel(group)));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(group -> Observable.fromIterable(parsePojoModel(group)));
    }

    private List<BaseViewModel> parsePojoModel(Group group) {
        List<BaseViewModel> items = new ArrayList<>();

        for (Link link : group.getLinks()) {

            items.add(new LinkAttachmentViewModel(link));

        }

        return items;
    }

    public Callable<Group> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            Group result = realm.where(Group.class)
                    .equalTo("id", Math.abs(ApiConstants.MY_GROUP_ID))
                    .findFirst();
            return realm.copyFromRealm(result);
        };
    }

}
