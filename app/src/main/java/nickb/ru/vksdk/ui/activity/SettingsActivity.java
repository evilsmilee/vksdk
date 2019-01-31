package nickb.ru.vksdk.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.ui.fragment.MyPreferencesFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected int getMainContentLayout() {
        return R.layout.activity_settings;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(R.id.main_wrapper, new MyPreferencesFragment())
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Настройки");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("Настройки");

        mFab.hide();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
