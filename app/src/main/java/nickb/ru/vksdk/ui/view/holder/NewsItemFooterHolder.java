package nickb.ru.vksdk.ui.view.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.common.utils.Utils;
import nickb.ru.vksdk.common.utils.VKListHelper;
import nickb.ru.vksdk.model.Place;
import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.model.countable.Likes;
import nickb.ru.vksdk.model.view.NewsItemFooterViewModel;
import nickb.ru.vksdk.model.view.counter.CommentCounterViewModel;
import nickb.ru.vksdk.model.view.counter.LikeCounterViewModel;
import nickb.ru.vksdk.model.view.counter.RepostCounterViewModel;
import nickb.ru.vksdk.rest.api.LikeEventOnSubscribe;
import nickb.ru.vksdk.rest.api.WallApi;
import nickb.ru.vksdk.rest.model.request.WallGetByIdRequestModel;
import nickb.ru.vksdk.ui.activity.BaseActivity;
import nickb.ru.vksdk.ui.fragment.CommentsFragment;

public class NewsItemFooterHolder extends BaseViewHolder<NewsItemFooterViewModel> {

    @BindView(R.id.tv_date)
    public TextView tvDate;
    @BindView(R.id.tv_likes_counts)
    public TextView tvLikesCount;
    @BindView(R.id.tv_likes_icon)
    public TextView tvLikesIcon;
    @BindView(R.id.tv_comments_icon)
    public TextView tvCommentsIcon;
    @BindView(R.id.tv_comments_counts)
    public TextView tvCommentsCount;
    @BindView(R.id.iv_reposted_icon)
    public TextView tvRepostIcon;
    @BindView(R.id.tv_reposts_counts)
    public TextView tvRepostsCount;

    @BindView(R.id.rl_likes)
    public View rlLikes;

    @Inject
    Typeface mGoogleFontTypeface;

    public static final String POST = "post";

    private Resources mResources;
    private Context mContext;

    @BindView(R.id.rl_comments)
    public View rlComments;

    @Inject
    MyFragmentManager mFragmentManager;

    @Inject
    WallApi mWallApi;

    public NewsItemFooterHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        MyApplication.getsApplicationComponent().inject(this);

        mContext = itemView.getContext();
        mResources = mContext.getResources();

        tvLikesIcon.setTypeface(mGoogleFontTypeface);
        tvCommentsIcon.setTypeface(mGoogleFontTypeface);
        tvRepostIcon.setTypeface(mGoogleFontTypeface);

    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Observable<LikeCounterViewModel> likeObservable(int ownerId, int postId, Likes likes) {
        return Observable.create(new LikeEventOnSubscribe(POST, ownerId, postId, likes))

                .observeOn(Schedulers.io())
                .flatMap(count -> {

                    return mWallApi.getById(new WallGetByIdRequestModel(ownerId, postId).toMap());
                })
                .flatMap(full -> Observable.fromIterable(VKListHelper.getWallList(full.response)))
                .doOnNext(this::saveToDb)
                .map(wallItem -> new LikeCounterViewModel(wallItem.getLikes()));
    }

    @SuppressLint("CheckResult")
    public void like(NewsItemFooterViewModel item) {
        WallItem wallItem = getWallItemFromRealm(item.getmId());
        likeObservable(wallItem.getOwnerId(), wallItem.getId(), wallItem.getLikes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(likes -> {
                    item.setLikes(likes);
                    bindLikes(likes);
                }, error -> error.printStackTrace());
    }

    public WallItem getWallItemFromRealm(int postId) {
        Realm realm = Realm.getDefaultInstance();
        WallItem wallItem = realm.where(WallItem.class)
                .equalTo("id", postId)
                .findFirst();

        return realm.copyFromRealm(wallItem);
    }


    @Override
    public void bindViewHolder(NewsItemFooterViewModel item) {
        tvDate.setText(Utils.parseDate(item.getDateLong(), mContext));

        bindLikes(item.getLikes());
        bindComments(item.getComments());
        bindReposts(item.getReposts());

        rlComments.setOnClickListener(view -> mFragmentManager.addFragment((BaseActivity) view.getContext(),
                CommentsFragment.newInstance(new Place(String.valueOf(item.getOwnerId()), String.valueOf(item.getmId()))),
                R.id.main_wrapper));

        rlLikes.setOnClickListener(view -> like(item));

    }

    private void bindLikes(LikeCounterViewModel likes) {
        tvLikesCount.setText(String.valueOf(likes.getCount()));
        tvLikesCount.setTextColor(mResources.getColor(likes.getTextColor()));
        tvLikesIcon.setTextColor(mResources.getColor(likes.getIconColor()));

    }
    private void bindComments(CommentCounterViewModel comments) {
        tvCommentsCount.setText(String.valueOf(comments.getCount()));
        tvCommentsCount.setTextColor(mResources.getColor(comments.getTextColor()));
        tvCommentsIcon.setTextColor(mResources.getColor(comments.getIconColor()));

    }
    private void bindReposts(RepostCounterViewModel reposts) {
        tvRepostsCount.setText(String.valueOf(reposts.getCount()));
        tvRepostsCount.setTextColor(mResources.getColor(reposts.getTextColor()));
        tvRepostIcon.setTextColor(mResources.getColor(reposts.getIconColor()));

    }


    @Override
    public void unbindViewHolder() {

        tvDate.setText(null);
        tvLikesCount.setText(null);
        tvCommentsCount.setText(null);
        tvRepostsCount.setText(null);

    }


}
