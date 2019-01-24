package nickb.ru.vksdk.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import nickb.ru.vksdk.CurrentUser;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.BaseAdapter;
import nickb.ru.vksdk.common.utils.VKListHelper;
import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.NewsItemBodyViewModel;
import nickb.ru.vksdk.model.view.NewsItemFooterViewModel;
import nickb.ru.vksdk.model.view.NewsItemHeaderViewModel;
import nickb.ru.vksdk.mvp.presenter.BaseFeedPresenter;
import nickb.ru.vksdk.mvp.presenter.NewsFeedPresenter;
import nickb.ru.vksdk.rest.api.WallApi;
import nickb.ru.vksdk.rest.model.request.WallGetRequestModel;
import nickb.ru.vksdk.rest.model.response.WallGetResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


}
