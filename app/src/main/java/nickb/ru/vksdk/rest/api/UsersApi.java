package nickb.ru.vksdk.rest.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import nickb.ru.vksdk.model.Profile;
import nickb.ru.vksdk.rest.model.response.Full;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface UsersApi {

    @GET(ApiMethods.USERS_GET)
    Observable<Full<List<Profile>>> get(@QueryMap Map<String, String> map);
}
