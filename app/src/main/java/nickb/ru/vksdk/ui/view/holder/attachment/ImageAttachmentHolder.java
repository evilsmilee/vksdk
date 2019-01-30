package nickb.ru.vksdk.ui.view.holder.attachment;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.model.view.attachment.ImageAttachmentViewModel;
import nickb.ru.vksdk.ui.activity.BaseActivity;
import nickb.ru.vksdk.ui.fragment.ImageFragment;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;

public class ImageAttachmentHolder extends BaseViewHolder<ImageAttachmentViewModel> {

    @BindView(R.id.iv_attachment_image)
    public ImageView image;

    @Inject
    MyFragmentManager myFragmentManager;

    public ImageAttachmentHolder(@NonNull View itemView) {
        super(itemView);

        MyApplication.getsApplicationComponent().inject(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindViewHolder(ImageAttachmentViewModel imageAttachmentViewModel) {

        Glide.with(itemView.getContext()).load(imageAttachmentViewModel.getPhotoUrl()).into(image);
        if (imageAttachmentViewModel.needClick) {
            itemView.setOnClickListener(v -> myFragmentManager.addFragment((BaseActivity) itemView.getContext(),
                    ImageFragment.newInstance(imageAttachmentViewModel.getPhotoUrl()), R.id.main_wrapper));
        }

    }

    @Override
    public void unbindViewHolder() {
        itemView.setOnClickListener(null);
        image.setImageBitmap(null);
    }
}
