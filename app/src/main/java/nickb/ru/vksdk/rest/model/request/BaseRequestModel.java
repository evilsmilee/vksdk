package nickb.ru.vksdk.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;
import java.util.Map;

import nickb.ru.vksdk.CurrentUser;
import nickb.ru.vksdk.consts.ApiConstants;

public abstract class BaseRequestModel {


    @SerializedName(VKApiConst.VERSION)
    Double version = ApiConstants.DEFAULT_VERSION;


    @SerializedName(VKApiConst.ACCESS_TOKEN)
    String accessToken = CurrentUser.getAccessToken();


    public Double getVersion() {
        return version;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();

        map.put(VKApiConst.VERSION, String.valueOf(getVersion()));
        if(accessToken != null) {
            map.put(VKApiConst.ACCESS_TOKEN, getAccessToken());
        }

        onMapCreate(map);

        return map;
    }

    public abstract void onMapCreate(Map<String, String> map);

}
