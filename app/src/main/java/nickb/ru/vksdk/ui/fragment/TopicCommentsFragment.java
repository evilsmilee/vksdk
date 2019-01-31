package nickb.ru.vksdk.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.model.Place;
import nickb.ru.vksdk.mvp.presenter.BaseFeedPresenter;
import nickb.ru.vksdk.mvp.presenter.TopicCommentsPresenter;

public class TopicCommentsFragment extends BaseFeedFragment {

    public static TopicCommentsFragment newInstance(Place place) {

        Bundle args = new Bundle();
        args.putAll(place.toBundle());

        TopicCommentsFragment fragment = new TopicCommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    TopicCommentsPresenter mPresenter;


    Place mPlace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);
        setWithEndlessList(true);

        mPlace = new Place(getArguments());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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

}
