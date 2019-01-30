package nickb.ru.vksdk.model.view.attachment;

import android.view.View;

import java.util.List;

import nickb.ru.vksdk.common.utils.Utils;
import nickb.ru.vksdk.model.attachment.doc.Doc;
import nickb.ru.vksdk.model.attachment.doc.Size;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;
import nickb.ru.vksdk.ui.view.holder.attachment.DocImageAttachmentHolder;

public class DocImageAttachmentViewModel extends BaseViewModel {

    private String mTitle;
    private String mSize;
    private String mExt;

    private String mImage;

    private String mUrl;


    public DocImageAttachmentViewModel(Doc doc) {
        if (doc.getTitle().equals("")) {
            mTitle = "Document";
        } else {
            mTitle = Utils.removeExtFromText(doc.getTitle());
        }

        mUrl = doc.getUrl();

        mSize = Utils.formatSize(doc.getSize());

        mExt = "." + doc.getExt();

        List<Size> sizes = doc.getPreview().getPhoto().getSizes();
        mImage = sizes.get(sizes.size() - 1).getSrc();
    }




    @Override
    public LayoutTypes getType() {
        return LayoutTypes.AttachmentDocImage;
    }

    @Override
    protected DocImageAttachmentHolder onCreateViewHolder(View view) {
        return new DocImageAttachmentHolder(view);
    }


    public String getmUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSize() {
        return mSize;
    }

    public String getExt() {
        return mExt;
    }

    public String getImage() {
        return mImage;
    }
}
