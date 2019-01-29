package nickb.ru.vksdk.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import nickb.ru.vksdk.model.attachment.video.Video;

public class VideosResponse {

    public int count;

    @SerializedName("items")
    @Expose
    public List<Video> items;

}
