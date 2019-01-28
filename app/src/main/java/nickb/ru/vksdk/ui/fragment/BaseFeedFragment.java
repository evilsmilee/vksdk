package nickb.ru.vksdk.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.BaseAdapter;
import nickb.ru.vksdk.common.manager.MyLinearLayoutManager;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.mvp.presenter.BaseFeedPresenter;
import nickb.ru.vksdk.mvp.view.BaseFeedView;

public abstract class BaseFeedFragment extends BaseFragment implements BaseFeedView {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    BaseAdapter mBaseAdapter;

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ProgressBar mProgressBar;

    protected BaseFeedPresenter mBaseFeedPresenter;

    private boolean isWithEndlessList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSwipeToRefreshLayout(view);
        setUpRecycelerView(view);
        setUpAdapter(mRecyclerView);
        ButterKnife.bind(this, view);
        mBaseFeedPresenter = onCreateFeedPresenter();
        mBaseFeedPresenter.loadStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isWithEndlessList = true;
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.fragment_feed;
    }

    @Override
    public int onCreateToolbarTitle() {
        return 0;
    }

    private void setUpRecycelerView(View rootView) {
        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(myLinearLayoutManager);

       if (isWithEndlessList) {
           mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
               @Override
               public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                   if (myLinearLayoutManager.isOnNextPagePosition()) {
                       mBaseFeedPresenter.loadNext(mBaseAdapter.getRealItemCount());
                   }
               }
           });
       }
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    protected void setUpAdapter(RecyclerView recyclerView) {
        mBaseAdapter = new BaseAdapter();
        recyclerView.setAdapter(mBaseAdapter);
    }

    private void setUpSwipeToRefreshLayout(View rootView) {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            onCreateFeedPresenter().loadRefresh();
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mProgressBar = getBaseActivity().getmProgressBar();
    }

    @Override
    public void showRefreshing() {

    }

    @Override
    public void hideRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showListProgress() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideListProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getBaseActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setItems(List<BaseViewModel> items) {
        mBaseAdapter.setItems(items);
    }

    @Override
    public void addItems(List<BaseViewModel> items) {
        mBaseAdapter.addItems(items);
    }

    protected abstract BaseFeedPresenter onCreateFeedPresenter();

    public boolean isWithEndlessList() {
        return isWithEndlessList;
    }

    public void setWithEndlessList(boolean withEndlessList) {
        isWithEndlessList = withEndlessList;
    }
}
