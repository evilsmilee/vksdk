package nickb.ru.vksdk.ui.view.holder;

import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.common.utils.UiHelper;
import nickb.ru.vksdk.model.view.NewsItemBodyViewModel;
import nickb.ru.vksdk.ui.activity.BaseActivity;
import nickb.ru.vksdk.ui.fragment.OpenedPostFragment;

public class NewsItemBodyHolder extends BaseViewHolder<NewsItemBodyViewModel> {

    @BindView(R.id.tv_text)
    public TextView tvText;
    @BindView(R.id.tv_attachments)
    public TextView tvAttachments;

    @Inject
    MyFragmentManager myFragmentManager;

    @Inject
    protected Typeface mFontGoogle;

    public NewsItemBodyHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        MyApplication.getsApplicationComponent().inject(this);

        if(tvAttachments != null) {
            tvAttachments.setTypeface(mFontGoogle);
        }
    }

    @Override
    public void bindViewHolder(NewsItemBodyViewModel item) {
            tvText.setText(item.getText());
            tvAttachments.setText(item.getmAttachmentString());
        itemView.setOnClickListener(view -> myFragmentManager.addFragment((BaseActivity) view.getContext(),
                OpenedPostFragment.newInstance(item.getId()),
                R.id.main_wrapper));
        UiHelper.getInstance().setUpTextViewWithVisibility(tvText, item.getText());
        UiHelper.getInstance().setUpTextViewWithVisibility(tvAttachments, item.getmAttachmentString());
    }

    @Override
    public void unbindViewHolder() {
            tvText.setText(null);
            tvAttachments.setText(null);
            itemView.setOnClickListener(null);
    }
}
