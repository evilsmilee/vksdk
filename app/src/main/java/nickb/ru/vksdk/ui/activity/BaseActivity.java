package nickb.ru.vksdk.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.androidx.MvpAppCompatActivity;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.ui.fragment.BaseFragment;


public abstract class BaseActivity extends MvpAppCompatActivity {

    @BindView(R.id.progress_base)
    protected ProgressBar mProgressBar;

    @Inject
    MyFragmentManager myFragmentManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    public FloatingActionButton mFab;

    @Inject
    LayoutInflater mLayoutInflater;

    @BindView(R.id.main_wrapper)
    FrameLayout mParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mLayoutInflater.inflate(getMainContentLayout(), mParent);
    }

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }

    @LayoutRes
    protected abstract int getMainContentLayout();




    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

    }

    public void setContent(BaseFragment fragment) {
        myFragmentManager.setFragment(this, fragment, R.id.main_wrapper);
    }

    public void addContent(BaseFragment fragment) {
        myFragmentManager.addFragment(this, fragment, R.id.main_wrapper);
    }

    public boolean removeCurrentFragment() {
        return myFragmentManager.removeCurrentFragment(this);
    }

    public boolean removeFragment(BaseFragment fragment) {
        return  myFragmentManager.removeFragment(this, fragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeCurrentFragment();
    }

    public void setupFabVisibility(boolean needFab) {
        if (mFab == null) return;

        if (needFab) {
            mFab.show();
        } else {
            mFab.hide();
        }
    }

    public void fragmentOnScreen(BaseFragment baseFragment) {
        setToolbarTitle(baseFragment.createToolbarTitle(this));
        setupFabVisibility(baseFragment.needFab());
    }

}
