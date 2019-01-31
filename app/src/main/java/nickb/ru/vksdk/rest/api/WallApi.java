package nickb.ru.vksdk.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import nickb.ru.vksdk.model.CommentItem;
import nickb.ru.vksdk.rest.model.response.Full;
import nickb.ru.vksdk.rest.model.response.ItemWithSendersResponse;
import nickb.ru.vksdk.rest.model.response.WallGetByIdResponse;
import nickb.ru.vksdk.rest.model.response.WallGetResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface WallApi {
    @GET(ApiMethods.WALL_GET)
    Observable<WallGetResponse> get(@QueryMap Map<String, String> map);


    @GET(ApiMethods.WALL_GET_BY_ID)
    Observable<WallGetByIdResponse> getById(@QueryMap  Map<String, String> map);

    @GET(ApiMethods.WALL_GET_COMMENTS)
    Observable<Full<ItemWithSendersResponse<CommentItem>>> getComments(@QueryMap Map<String, String> map);
}
