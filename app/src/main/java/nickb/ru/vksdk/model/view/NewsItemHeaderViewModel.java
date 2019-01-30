package nickb.ru.vksdk.model.view;

import android.view.View;

import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;
import nickb.ru.vksdk.ui.view.holder.NewsItemHeaderHolder;

public class NewsItemHeaderViewModel extends BaseViewModel {

    private int mId;

    private String mProfilePhoto;

    private String mProfileName;

    private boolean mIsRepost;
    private String mRepostProfileName;

    public NewsItemHeaderViewModel(WallItem wallItem) {
        this.mId = wallItem.getId();
        this.mProfilePhoto = wallItem.getSenderPhoto();
        this.mProfileName = wallItem.getSenderName();
        this.mIsRepost = wallItem.haveSharedRepost();
        if(mIsRepost) {
            this.mRepostProfileName = wallItem.getSharedRepost().getSenderName();
        }

    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.NewsFeedItemHeader;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new NewsItemHeaderHolder(view);
    }

    public int getId() {
        return mId;
    }

    public String getProfilePhoto() {
        return mProfilePhoto;
    }

    public String getProfileName() {
        return mProfileName;
    }

    public boolean IsRepost() {
        return mIsRepost;
    }

    public String getmRepostProfileName() {
        return mRepostProfileName;
    }


}
