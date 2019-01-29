package nickb.ru.vksdk.consts;

import com.vk.sdk.VKScope;

public class ApiConstants {
    public static final String[] DEFAULT_LOGIN_SCOPE =  {VKScope.EMAIL, VKScope.AUDIO, VKScope.VIDEO, VKScope.DIRECT,
            VKScope.DOCS, VKScope.MESSAGES, VKScope.PHOTOS, VKScope.GROUPS, VKScope.PAGES, VKScope.STATS};
    public static final Double DEFAULT_VERSION = 5.67;
    public static final int DEFAULT_COUNT = 10;
    public static final String DEFAULT_USER_FIELDS = "photo_100";
    public static final String DEFAULT_MEMBER_FIELDS = "name,photo_100";
    public static final int MY_GROUP_ID = -86529522; //-125227382;
    public static final String DEFAULT_GROUP_FIELDS = "status,description,site,links,contacts";
    public static final String VIDEOS = "videos";
    public static final String POSTS = "posts";
    public static final String EXTENDED = "extended";

}
