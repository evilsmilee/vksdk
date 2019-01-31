package nickb.ru.vksdk.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.common.utils.VKListHelper;
import nickb.ru.vksdk.model.CommentItem;
import nickb.ru.vksdk.model.Place;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.CommentBodyViewModel;
import nickb.ru.vksdk.model.view.CommentFooterViewModel;
import nickb.ru.vksdk.model.view.CommentHeaderViewModel;
import nickb.ru.vksdk.mvp.view.BaseFeedView;
import nickb.ru.vksdk.rest.api.BoardApi;
import nickb.ru.vksdk.rest.model.request.BoardGetCommentsRequestModel;

@InjectViewState
public class TopicCommentsPresenter extends  BaseFeedPresenter<BaseFeedView> {

    private Place mPlace;

    @Inject
    BoardApi mBoardApi;


    public TopicCommentsPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mBoardApi.getComments(new BoardGetCommentsRequestModel(
                Integer.parseInt(mPlace.getOwnerId()), Integer.parseInt(mPlace.getPostId()), offset).toMap())
                .flatMap(full -> Observable.fromIterable(VKListHelper.getCommentsList(full.response, true)))
                .doOnNext(commentItem -> commentItem.setPlace(mPlace))
                .doOnNext(this::saveToDb)
                .flatMap(commentItem -> Observable.fromIterable(parsePojoModel(commentItem)));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .filter(commentItem -> commentItem.getPlace().equals(this.mPlace) && commentItem.isFromTopic)
                .flatMap(commentItem -> Observable.fromIterable(parsePojoModel(commentItem)));
    }


    private List<BaseViewModel> parsePojoModel(CommentItem commentItem) {
        List<BaseViewModel> baseItems = new ArrayList<>();
        baseItems.add(new CommentHeaderViewModel(commentItem));
        baseItems.add(new CommentBodyViewModel(commentItem));
        baseItems.add(new CommentFooterViewModel(commentItem));
        return baseItems;
    }


    public Callable<List<CommentItem>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {"id"};
            Sort[] sortOrder = {Sort.ASCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<CommentItem> results = realm.where(CommentItem.class)
                    .sort(sortFields, sortOrder)
                    .findAll();
            return realm.copyFromRealm(results);
        };
    }


    public void setPlace(Place place) {
        this.mPlace = place;
    }

}
