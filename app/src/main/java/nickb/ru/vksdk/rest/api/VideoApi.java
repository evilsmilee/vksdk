package nickb.ru.vksdk.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import nickb.ru.vksdk.rest.model.response.Full;
import nickb.ru.vksdk.rest.model.response.VideosResponse;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface VideoApi {

    @GET(ApiMethods.VIDEO_GET)
    Observable<Full<VideosResponse>> get(@QueryMap Map<String, String> map);

}
