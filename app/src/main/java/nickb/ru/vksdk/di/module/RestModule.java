package nickb.ru.vksdk.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nickb.ru.vksdk.rest.RestClient;
import nickb.ru.vksdk.rest.api.BoardApi;
import nickb.ru.vksdk.rest.api.GroupsApi;
import nickb.ru.vksdk.rest.api.UsersApi;
import nickb.ru.vksdk.rest.api.WallApi;

@Module
public class RestModule {

    private RestClient mRestClient;

    public RestModule() {
        mRestClient = new RestClient();
    }

    @Singleton
    @Provides
    public RestClient provideRestClient() {
        return mRestClient;
    }

    @Singleton
    @Provides
    public WallApi provideWallApi() {
        return mRestClient.createService(WallApi.class);
    }

    @Singleton
    @Provides
    public UsersApi provideUsersApi() {
        return mRestClient.createService(UsersApi.class);
    }

    @Singleton
    @Provides
    public GroupsApi provideGroupsApi() { return mRestClient.createService(GroupsApi.class);}

    @Singleton
    @Provides
    public BoardApi provideBoardApi() {return  mRestClient.createService(BoardApi.class);}

}
