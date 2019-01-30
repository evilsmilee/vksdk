package nickb.ru.vksdk.model.view.attachment;

import android.view.View;

import nickb.ru.vksdk.model.attachment.Page;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.ui.view.holder.attachment.PageAttachmentHolder;

public class PageAttachmentViewModel extends BaseViewModel {

    private String mTitle;
    private String mUrl;

    public PageAttachmentViewModel(Page page) {
        mUrl = page.getUrl();
        mTitle = page.getTitle();
    }

    public String getTitle() {
        return mTitle;
    }


    @Override
    public LayoutTypes getType() {
        return LayoutTypes.AttachmentPage;
    }

    @Override
    protected PageAttachmentHolder onCreateViewHolder(View view) {
        return new PageAttachmentHolder(view);
    }


    public String getmUrl() {
        return mUrl;
    }
}
