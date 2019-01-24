package nickb.ru.vksdk.common.utils;

import java.util.List;

import nickb.ru.vksdk.model.Owner;
import nickb.ru.vksdk.model.WallItem;
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

}
