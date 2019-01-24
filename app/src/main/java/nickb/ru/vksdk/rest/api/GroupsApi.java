package nickb.ru.vksdk.rest.api;

import java.util.Map;

import io.reactivex.Observable;
import nickb.ru.vksdk.model.Member;
import nickb.ru.vksdk.rest.model.response.BaseItemResponse;
import nickb.ru.vksdk.rest.model.response.Full;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GroupsApi {

    @GET(ApiMethods.GROUPS_GET_MEMBERS)
    Observable<Full<BaseItemResponse<Member>>> getMembers(@QueryMap Map<String, String> map);
}
