package nickb.ru.vksdk.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.model.Place;
import nickb.ru.vksdk.mvp.presenter.BaseFeedPresenter;
import nickb.ru.vksdk.mvp.presenter.CommentsPresenter;
import nickb.ru.vksdk.ui.activity.CreatePostActivity;

public class CommentsFragment extends BaseFeedFragment {

    public static CommentsFragment newInstance(Place place) {

        Bundle args = new Bundle();
        args.putAll(place.toBundle());

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    CommentsPresenter mPresenter;

    Place mPlace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);

        mPlace = new Place(getArguments());
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected BaseFeedPresenter onCreateFeedPresenter() {
        mPresenter.setPlace(mPlace);
        return mPresenter;
    }


    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_comments;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseActivity().mFab.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseActivity(), CreatePostActivity.class);
            intent.putExtra("type", "comment");
            intent.putExtra("owner_id", Integer.parseInt(mPlace.getOwnerId()));
            intent.putExtra("id", Integer.parseInt(mPlace.getPostId()));
            startActivityForResult(intent, 0);
        });
    }

    @Override
    public boolean needFab() {
        return true;
    }

}
