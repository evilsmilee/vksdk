package nickb.ru.vksdk.common.utils;

import com.vk.sdk.api.model.VKAttachments;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import nickb.ru.vksdk.model.Owner;
import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.model.attachment.ApiAttachment;
import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.model.view.attachment.AudioAttachmentViewModel;
import nickb.ru.vksdk.model.view.attachment.DocAttachmentViewModel;
import nickb.ru.vksdk.model.view.attachment.DocImageAttachmentViewModel;
import nickb.ru.vksdk.model.view.attachment.ImageAttachmentViewModel;
import nickb.ru.vksdk.model.view.attachment.LinkAttachmentViewModel;
import nickb.ru.vksdk.model.view.attachment.LinkExternalViewModel;
import nickb.ru.vksdk.model.view.attachment.PageAttachmentViewModel;
import nickb.ru.vksdk.model.view.attachment.VideoAttachmentViewModel;
import nickb.ru.vksdk.rest.model.response.ItemWithSendersResponse;

public class VKListHelper {

    public static List<WallItem> getWallList(ItemWithSendersResponse<WallItem> response) {
        List<WallItem> wallItems = response.items;

        for (WallItem wallItem : wallItems) {
            Owner sender = response.getSender(wallItem.getFromId());
            wallItem.setSenderName(sender.getFullName());
            wallItem.setSenderPhoto(sender.getPhoto());

            wallItem.setAttachmentString(Utils.convertAttachmentsToFontIcons(wallItem.getAttachments()));

            if(wallItem.haveSharedRepost()) {
                Owner repostSender = response.getSender(wallItem.getSharedRepost().getFromId());
                wallItem.getSharedRepost().setSenderName(repostSender.getFullName());
                wallItem.getSharedRepost().setSenderPhoto(repostSender.getPhoto());
                wallItem.getSharedRepost().setAttachmentString(Utils.convertAttachmentsToFontIcons(wallItem.getSharedRepost().getAttachments()));
            }
        }

            return wallItems;
    }

    public static List<BaseViewModel> getAttachmentVhItems(List<ApiAttachment> attachments) {

        List<BaseViewModel> attachmentVhItems = new ArrayList<>();
        for (ApiAttachment attachment : attachments) {

            switch (attachment.getType()) {
                case VKAttachments.TYPE_PHOTO:
                    attachmentVhItems.add(new ImageAttachmentViewModel(attachment.getPhoto()));
                    break;

                case VKAttachments.TYPE_AUDIO:
                    attachmentVhItems.add(new AudioAttachmentViewModel(attachment.getAudio()));
                    break;

                case VKAttachments.TYPE_VIDEO:
                    attachmentVhItems.add(new VideoAttachmentViewModel(attachment.getVideo()));
                    break;

                case VKAttachments.TYPE_DOC:
                    if (attachment.getDoc().getPreview() != null) {
                        attachmentVhItems.add(new DocImageAttachmentViewModel(attachment.getDoc()));
                    } else {
                        attachmentVhItems.add(new DocAttachmentViewModel(attachment.getDoc()));
                    }
                    break;

                case VKAttachments.TYPE_LINK:
                    if (attachment.getLink().getIsExternal() == 1) {
                        attachmentVhItems.add(new LinkExternalViewModel(attachment.getLink()));
                    } else {
                        attachmentVhItems.add(new LinkAttachmentViewModel(attachment.getLink()));
                    }
                    break;

                case "page":
                    attachmentVhItems.add(new PageAttachmentViewModel(attachment.getPage()));
                    break;

                default:
                    throw new NoSuchElementException("Attachment type " + attachment.getType() + " is not supported.");
            }
        }
        return attachmentVhItems;
    }


}
