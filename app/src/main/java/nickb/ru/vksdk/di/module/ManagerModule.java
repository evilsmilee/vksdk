package nickb.ru.vksdk.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.common.manager.NetworkManager;

@Module
public class ManagerModule {

    @Singleton
    @Provides
    MyFragmentManager provideMyFragmentManager() {
        return new MyFragmentManager();
    }

    @Provides
    @Singleton
    NetworkManager provideNetworkManager() {
        return new NetworkManager();
    }
}
