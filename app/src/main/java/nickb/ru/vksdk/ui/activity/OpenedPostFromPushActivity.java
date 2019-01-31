package nickb.ru.vksdk.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.model.Place;
import nickb.ru.vksdk.ui.fragment.OpenedPostFragment;

//активити, которое открывается при клике на уведомлении
public class OpenedPostFromPushActivity extends BaseActivity {

    @Inject
    MyFragmentManager myFragmentManager;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);

        //из интента получаем place
        Bundle bundle = getIntent().getBundleExtra(Place.PLACE);

        Place place = new Place(bundle);
        //открываем пост по id из place
        myFragmentManager.addFragment(this,
                OpenedPostFragment.newInstance(Integer.valueOf(place.getPostId())),
                R.id.main_wrapper);
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.activity_opened_post_from_push;
    }

    @Override
    public void onBackPressed() {

        Log.d("BACKSTACK", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));

        //управляем стеком переходов назад, из открытого поста выходим в приложение
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


}
