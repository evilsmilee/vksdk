package nickb.ru.vksdk.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.mvp.presenter.BaseFeedPresenter;
import nickb.ru.vksdk.mvp.presenter.NewsFeedPresenter;
import nickb.ru.vksdk.rest.api.WallApi;
import nickb.ru.vksdk.ui.activity.CreatePostActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends BaseFeedFragment {




    @Inject
    WallApi mWallApi;

    @InjectPresenter
    NewsFeedPresenter mPresenter;

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_news;
    }

    @Override
    protected BaseFeedPresenter onCreateFeedPresenter() {
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseActivity().mFab.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), CreatePostActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    @Override
    public boolean needFab() {
        return true;
    }


}
