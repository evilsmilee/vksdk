package nickb.ru.vksdk.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import nickb.ru.vksdk.R;

public class MyPreferencesFragment extends PreferenceFragment {


    public MyPreferencesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }


}
