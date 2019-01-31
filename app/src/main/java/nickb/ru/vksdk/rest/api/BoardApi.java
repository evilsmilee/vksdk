package nickb.ru.vksdk.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import nickb.ru.vksdk.model.CommentItem;
import nickb.ru.vksdk.model.Topic;
import nickb.ru.vksdk.rest.model.response.BaseItemResponse;
import nickb.ru.vksdk.rest.model.response.Full;
import nickb.ru.vksdk.rest.model.response.ItemWithSendersResponse;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface BoardApi {

    @GET(ApiMethods.BOARD_GET_TOPICS)
    Observable<Full<BaseItemResponse<Topic>>> getTopic(@QueryMap Map<String, String> map);

    @GET(ApiMethods.BOARD_GET_COMMENTS)
    Observable<Full<ItemWithSendersResponse<CommentItem>>> getComments(@QueryMap Map<String, String> map);

}
