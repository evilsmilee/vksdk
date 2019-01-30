package nickb.ru.vksdk.model.view;

import android.view.View;

import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.ui.view.holder.NewsItemBodyHolder;

public class NewsItemBodyViewModel extends BaseViewModel {
    private int mId;

    private String mText;



    private String mAttachmentString;

    private boolean mIsRepost;

    public NewsItemBodyViewModel(WallItem wallItem) {
        this.mId = wallItem.getId();
        this.mIsRepost = wallItem.haveSharedRepost();

        if(mIsRepost) {
            this.mText = wallItem.getSharedRepost().getText();
            this.mAttachmentString = wallItem.getSharedRepost().getAttachmentString();
        } else {
            this.mText = wallItem.getText();
            this.mAttachmentString = wallItem.getAttachmentString();
        }
    }


    @Override
    public LayoutTypes getType() {
        return LayoutTypes.NewsFeedItemBody;
    }

    @Override
    public NewsItemBodyHolder onCreateViewHolder(View view) {
        return new NewsItemBodyHolder(view);
    }


    public String getText() {
        return mText;
    }

    public int getId() {
        return mId;
    }

    public String getmAttachmentString() {
        return mAttachmentString;
    }

    @Override
    public boolean isItemDecorator() {
        return true;
    }
}
