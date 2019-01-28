package nickb.ru.vksdk.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.Map;

import nickb.ru.vksdk.consts.ApiConstants;

public class GroupGetByIdRequestModel extends BaseRequestModel {

    @SerializedName(VKApiConst.GROUP_ID)
    int groupID;

    @SerializedName(VKApiConst.FIELDS)
    String fields = ApiConstants.DEFAULT_GROUP_FIELDS;

    public GroupGetByIdRequestModel(int groupID) {
        this.groupID = Math.abs(groupID);
    }

    public int getGroupID() {
        return Math.abs(groupID);
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    @Override
    public void onMapCreate(Map<String, String> map) {
            map.put(VKApiConst.GROUP_ID, String.valueOf(getGroupID()));
            map.put(VKApiConst.FIELDS, String.valueOf(getFields()));
    }
}
