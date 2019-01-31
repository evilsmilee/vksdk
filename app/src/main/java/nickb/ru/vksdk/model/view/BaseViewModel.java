package nickb.ru.vksdk.model.view;

import androidx.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nickb.ru.vksdk.R;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;

public abstract class BaseViewModel {

    public abstract LayoutTypes getType();

    public BaseViewHolder createViewHolder(ViewGroup parent) {
          return   onCreateViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(getType().getValue(), parent, false));

    }

    protected abstract BaseViewHolder onCreateViewHolder(View view);

    public enum LayoutTypes {
        NewsFeedItemHeader(R.layout.item_news_header),
        NewsFeedItemBody(R.layout.item_news_body),
        NewsFeedItemFooter(R.layout.item_news_footer),
        Member(R.layout.item_member),
        InfoStatus(R.layout.item_info_status),
        InfoContacts(R.layout.item_info_contacts),
        InfoLinks(R.layout.item_info_links),
        Topic(R.layout.item_topic),

        AttachmentAudio(R.layout.item_attachment_audio),
        AttachmentDoc(R.layout.item_attachment_doc),
        AttachmentDocImage(R.layout.item_attachment_doc_image),
        AttachmentImage(R.layout.item_attachment_image),
        AttachmentLink(R.layout.item_attachment_link),
        AttachmentLinkExternal(R.layout.item_attachment_link_external),
        AttachmentPage(R.layout.item_attachment_page),
        AttachmentVideo(R.layout.item_attachment_video),

        CommentHeader(R.layout.item_comment_header),
        CommentBody(R.layout.item_comment_body),
        CommentFooter(R.layout.item_comment_footer),

        OpenedPostHeader(R.layout.item_opened_post_header),
        OpenedPostRepostHeader(R.layout.item_opened_post_repost_header);


        private final int id;

        LayoutTypes(int resId) {
            this.id = resId;
        }

        @LayoutRes
        public int getValue() {
            return id;
        }
    }

    public boolean isItemDecorator() {
        return false;
    }
}
