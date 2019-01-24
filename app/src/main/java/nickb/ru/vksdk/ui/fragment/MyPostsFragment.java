package nickb.ru.vksdk.ui.fragment;

import android.os.Bundle;

import nickb.ru.vksdk.R;

public class MyPostsFragment extends NewsFeedFragment {

    public MyPostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setEnableFiltering(true);
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_my_posts;
    }
}
