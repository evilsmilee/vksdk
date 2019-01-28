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
import nickb.ru.vksdk.model.Topic;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.MemberViewModel;
import nickb.ru.vksdk.model.view.TopicViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;
import nickb.ru.vksdk.rest.api.BoardApi;
import nickb.ru.vksdk.rest.model.request.BoardGetTopicsRequestModel;


@InjectViewState
public class BoardPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    BoardApi mBoardApi;

    public BoardPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mBoardApi.getTopic(new BoardGetTopicsRequestModel(ApiConstants.MY_GROUP_ID, count, offset
        ).toMap())
                .flatMap(baseItemResponseFull ->
                        Observable.fromIterable(baseItemResponseFull.response.getItems()))
                .doOnNext(topic -> topic.setGroupId(ApiConstants.MY_GROUP_ID))
                .doOnNext(this::saveToDb)
                .map(TopicViewModel::new);
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .map(TopicViewModel::new);
}

    public Callable<List<Topic>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {Member.ID};
            Sort[] sortOrder = {Sort.DESCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Topic> results = realm.where(Topic.class)
                    .equalTo("groupId", ApiConstants.MY_GROUP_ID)
                    .sort(sortFields, sortOrder)
                    .findAll();
            return realm.copyFromRealm(results);
        };
    }

}
