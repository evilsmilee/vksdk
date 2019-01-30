package nickb.ru.vksdk.model.view.attachment;

import android.view.View;

import nickb.ru.vksdk.model.attachment.Photo;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;
import nickb.ru.vksdk.ui.view.holder.attachment.ImageAttachmentHolder;

public class ImageAttachmentViewModel extends BaseViewModel {

    private String mPhotoUrl;
    public boolean needClick = true;

    public ImageAttachmentViewModel(String url) {

        mPhotoUrl = url;
        needClick = false;
    }

    public ImageAttachmentViewModel(Photo photo) {

        mPhotoUrl = photo.getPhoto604();

    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.AttachmentImage;
    }

    @Override
    protected ImageAttachmentHolder onCreateViewHolder(View view) {
        return new ImageAttachmentHolder(view);
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

}
