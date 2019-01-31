package nickb.ru.vksdk.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import nickb.ru.vksdk.rest.model.response.Full;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AccountApi {
    @GET(ApiMethods.ACCOUNT_REGISTER_DEVICE)
    Observable<Full<Integer>> registerDevice(@QueryMap Map<String, String> map);
}
