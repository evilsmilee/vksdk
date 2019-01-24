package nickb.ru.vksdk;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.vk.sdk.VKSdk;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import nickb.ru.vksdk.di.component.ApplicationComponent;
import nickb.ru.vksdk.di.component.DaggerApplicationComponent;
import nickb.ru.vksdk.di.module.ApplicationModule;

public class MyApplication extends Application {

    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        VKSdk.initialize(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext()).load(uri).into(imageView);
            }
        });
    }

    private void initComponent() {
        sApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationComponent getsApplicationComponent() {
        return sApplicationComponent;
    }
}
