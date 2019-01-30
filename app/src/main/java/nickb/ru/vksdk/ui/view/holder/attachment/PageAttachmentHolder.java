package nickb.ru.vksdk.ui.view.holder.attachment;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.utils.Utils;
import nickb.ru.vksdk.model.view.attachment.PageAttachmentViewModel;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;

public class PageAttachmentHolder extends BaseViewHolder<PageAttachmentViewModel> {

    @BindView(R.id.tv_title)
    public TextView title;

    public PageAttachmentHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindViewHolder(PageAttachmentViewModel pageAttachmentViewModel) {
        itemView.setOnClickListener(view -> Utils.openUrlInActionView(pageAttachmentViewModel.getmUrl(), view.getContext()));
        title.setText(pageAttachmentViewModel.getTitle());
    }

    @Override
    public void unbindViewHolder() {
        itemView.setOnClickListener(null);
        title.setText(null);
    }


}
